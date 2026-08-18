from pathlib import Path

import chromadb
from sentence_transformers import SentenceTransformer


VECTOR_DB_DIR = Path("data/chroma_db")
COLLECTION_NAME = "jal_drishti_documents"

# Local embedding model — no Gemini quota needed
EMBEDDING_MODEL = "all-MiniLM-L6-v2"

embedding_model = SentenceTransformer(
    EMBEDDING_MODEL
)

chroma_client = chromadb.PersistentClient(
    path=str(VECTOR_DB_DIR)
)

collection = chroma_client.get_or_create_collection(
    name=COLLECTION_NAME
)


def generate_embeddings(
    texts: list[str],
) -> list[list[float]]:

    embeddings = embedding_model.encode(
        texts,
        batch_size=64,
        show_progress_bar=True,
        normalize_embeddings=True
    )

    return embeddings.tolist()


def add_chunks(chunks) -> int:

    if not chunks:
        return 0

    documents = [
        chunk.text
        for chunk in chunks
    ]

    ids = [
        f"{chunk.source}_{chunk.page}_{chunk.chunk_index}"
        for chunk in chunks
    ]

    metadatas = [
        {
            "source": chunk.source,
            "page": chunk.page,
            "chunk_index": chunk.chunk_index,
        }
        for chunk in chunks
    ]

    embeddings = generate_embeddings(
        documents
    )

    collection.upsert(
        ids=ids,
        documents=documents,
        metadatas=metadatas,
        embeddings=embeddings,
    )

    return len(chunks)


def search_similar(
    query: str,
    top_k: int = 5,
):
    query_embedding = generate_embeddings(
        [query]
    )[0]

    return collection.query(
        query_embeddings=[query_embedding],
        n_results=top_k,
    )