from pathlib import Path

import chromadb
from sentence_transformers import SentenceTransformer

from app.rag.chunker import DocumentChunk


VECTOR_DB_DIR = Path("data/chroma_db")
COLLECTION_NAME = "jal_drishti_documents"

_embedding_model = SentenceTransformer(
    "all-MiniLM-L6-v2"
)

_client = chromadb.PersistentClient(
    path=str(VECTOR_DB_DIR)
)

_collection = _client.get_or_create_collection(
    name=COLLECTION_NAME
)


def add_chunks(
    chunks: list[DocumentChunk],
) -> int:

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

    embeddings = _embedding_model.encode(
        documents
    ).tolist()

    _collection.upsert(
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
    query_embedding = _embedding_model.encode(
        [query]
    ).tolist()

    return _collection.query(
        query_embeddings=query_embedding,
        n_results=top_k,
    )