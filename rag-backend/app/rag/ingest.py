from pathlib import Path

import fitz
import pytesseract
from PIL import Image
from pypdf import PdfReader

from app.rag.chunker import chunk_text
from app.rag.vector_store import add_chunks


DOCUMENTS_DIR = Path("data/documents")
TESSERACT_PATH = r"C:\Program Files\Tesseract-OCR\tesseract.exe"


def extract_page_text(
    pdf_path: Path,
) -> list[tuple[int, str]]:
    """
    Extract text page-by-page.

    First tries normal PDF text extraction.
    If a page has no extractable text, OCR is used for that page.
    """

    pytesseract.pytesseract.tesseract_cmd = TESSERACT_PATH

    reader = PdfReader(str(pdf_path))
    pdf = fitz.open(str(pdf_path))

    extracted_pages = []

    for index, reader_page in enumerate(reader.pages):

        page_number = index + 1

        text = reader_page.extract_text() or ""

        if not text.strip():

            print(
                f"  OCR page "
                f"{page_number}/{len(reader.pages)}"
            )

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
                (
                    page_number,
                    text.strip(),
                )
            )

    pdf.close()

    return extracted_pages


def process_pdf(
    pdf_path: Path,
) -> int:

    print(f"\nProcessing: {pdf_path.name}")

    pages = extract_page_text(pdf_path)

    total_chunks = 0

    for page_number, text in pages:

        chunks = chunk_text(
            text=text,
            source=pdf_path.name,
            page=page_number,
            chunk_size=1000,
            overlap=150,
        )

        if not chunks:
            continue

        added = add_chunks(chunks)

        total_chunks += added

    print(
        f"  → {len(pages)} pages extracted"
    )

    print(
        f"  → {total_chunks} chunks stored"
    )

    return total_chunks


def load_and_index_documents() -> int:

    if not DOCUMENTS_DIR.exists():

        DOCUMENTS_DIR.mkdir(
            parents=True,
            exist_ok=True,
        )

    pdf_files = sorted(
        DOCUMENTS_DIR.glob("*.pdf")
    )

    print(
        f"PDF files discovered: "
        f"{len(pdf_files)}"
    )

    total_chunks = 0

    for pdf_path in pdf_files:

        total_chunks += process_pdf(
            pdf_path
        )

    return total_chunks


if __name__ == "__main__":

    total = load_and_index_documents()

    print(
        f"\nTOTAL CHUNKS STORED: {total}"
    )