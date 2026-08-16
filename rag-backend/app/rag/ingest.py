from pathlib import Path
from pypdf import PdfReader


DOCUMENTS_DIR = Path("data/documents")


def extract_text_from_pdf(pdf_path: Path) -> str:
    reader = PdfReader(str(pdf_path))

    pages = []

    for page in reader.pages:
        text = page.extract_text()

        if text:
            pages.append(text)

    return "\n\n".join(pages)


def load_documents() -> list[dict]:
    documents = []

    if not DOCUMENTS_DIR.exists():
        DOCUMENTS_DIR.mkdir(
            parents=True,
            exist_ok=True
        )

    for pdf_path in DOCUMENTS_DIR.glob("*.pdf"):

        text = extract_text_from_pdf(pdf_path)

        if not text.strip():
            continue

        documents.append({
            "source": pdf_path.name,
            "text": text
        })

    return documents


if __name__ == "__main__":

    documents = load_documents()

    print(f"PDFs found: {len(documents)}")

    for document in documents:
        print(
            f"{document['source']} "
            f"→ {len(document['text'])} characters"
        )