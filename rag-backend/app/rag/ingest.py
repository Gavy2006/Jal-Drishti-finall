from pathlib import Path

import fitz
import pytesseract
from PIL import Image
from pypdf import PdfReader

from app.rag.chunker import chunk_text
from app.rag.vector_store import add_chunks


DOCUMENTS_DIR = Path("data/documents")
TESSERACT_PATH = r"C:\Program Files\Tesseract-OCR\tesseract.exe"


def extract_page_text(pdf_path: Path) -> list[tuple[int, str]]:
    reader = PdfReader(str(pdf_path))
    pdf = fitz.open(str(pdf_path))

    extracted_pages = []

    pytesseract.pytesseract.tesseract_cmd = TESSERACT_PATH

    for index, reader_page in enumerate(reader.pages):
        page_number = index + 1
        text = reader_page.extract_text() or ""

        if not text.strip():
            print(f"  OCR page {page_number}/{len(reader.pages)}")

            page = pdf[index]

            pix = page.get_pixmap(
                matrix=fitz.Matrix(2, 2),
                alpha=False,
            )

            image = Image.frombytes(
                "RGB",
                [pix.width, pix.height],
                pix.samples,
            )

            text = pytesseract.image_to_string(
                image,
                lang="eng",
            )

        if text.strip():
            extracted_pages.append(
                (page_number, text.strip())
            )

    pdf.close()
    return extracted_pages


def load_all_chunks():
    pdf_files = sorted(DOCUMENTS_DIR.glob("*.pdf"))

    print(f"PDF files discovered: {len(pdf_files)}")

    all_chunks = []

    for pdf_path in pdf_files:
        print(f"\nProcessing: {pdf_path.name}")

        pages = extract_page_text(pdf_path)

        print(
            f"  → {len(pages)} pages extracted"
        )

        for page_number, text in pages:
            chunks = chunk_text(
                text=text,
                source=pdf_path.name,
                page=page_number,
                chunk_size=1000,
                overlap=150,
            )

            all_chunks.extend(chunks)

        print(
            f"  → cumulative chunks: {len(all_chunks)}"
        )

    return all_chunks


def index_all_chunks(chunks):
    batch_size = 50
    total = 0

    for i in range(0, len(chunks), batch_size):
        batch = chunks[i:i + batch_size]

        print(
            f"\nIndexing batch "
            f"{i + 1}-{i + len(batch)} / {len(chunks)}"
        )

        total += add_chunks(batch)

    return total


if __name__ == "__main__":
    all_chunks = load_all_chunks()

    print(
        f"\nTOTAL CHUNKS CREATED: {len(all_chunks)}"
    )

    total = index_all_chunks(all_chunks)

    print(
        f"\nTOTAL CHUNKS STORED: {total}"
    )