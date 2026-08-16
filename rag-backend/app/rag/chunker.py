from dataclasses import dataclass


@dataclass
class DocumentChunk:
    text: str
    source: str
    page: int
    chunk_index: int


def chunk_text(
    text: str,
    source: str,
    page: int,
    chunk_size: int = 1000,
    overlap: int = 150,
) -> list[DocumentChunk]:

    text = text.strip()

    if not text:
        return []

    chunks = []
    start = 0
    chunk_index = 0

    while start < len(text):

        end = min(
            start + chunk_size,
            len(text)
        )

        chunk = text[start:end].strip()

        if chunk:
            chunks.append(
                DocumentChunk(
                    text=chunk,
                    source=source,
                    page=page,
                    chunk_index=chunk_index,
                )
            )

            chunk_index += 1

        if end >= len(text):
            break

        start = end - overlap

    return chunks