import pandas as pd
import psycopg2
import os

base_dir = os.path.dirname(__file__)
csv_path = os.path.join(base_dir, 'Dati_Libri.csv')

db_params = {
    'dbname': 'LaboratorioB',
    'user': input("Inserisci username del database: "),
    'password': input("Inserisci password del database: "),
    'host': 'localhost',
    'port': 5432
}

# 1️ Leggi il CSV
df = pd.read_csv(csv_path, sep=';')

# 2️ Aggiungi un ID incrementale
df.insert(0, 'id_libro', range(1, len(df) + 1))

# 3️ Connessione a PostgreSQL
conn = psycopg2.connect(**db_params)
cur = conn.cursor()

# 4️ Inserimento dati con controllo
insert_query = """
    INSERT INTO libri (id_libro, titolo, autore, anno, genere, editore)
    VALUES (%s, %s, %s, %s, %s, %s)
"""

def safe_int(x):
    """Prova a convertire in intero, ritorna None se fallisce"""
    try:
        return int(float(x))
    except:
        return None

# Conta righe inserite e ignorate
righe_inserite = 0
righe_ignorate = 0

for _, row in df.iterrows():
    id_libro = safe_int(row.iloc[0])
    anno = safe_int(row.iloc[5])

    # Controlla se ci sono valori problematici
    if id_libro is None or anno is None:
        righe_ignorate += 1
        continue  # salta questa riga

    try:
        cur.execute(insert_query, (
            id_libro,
            row.iloc[1],  # titolo
            row.iloc[2],  # autore
            anno,
            row.iloc[3],  # genere
            row.iloc[4]   # editore
        ))
        righe_inserite += 1
    except Exception as e:
        print(f" Riga ignorata per errore: {row.to_dict()}")
        print("Dettagli:", e)
        righe_ignorate += 1
        continue

# 5️ Commit e chiusura connessione
conn.commit()
cur.close()
conn.close()

print(f" Inserite {righe_inserite} righe con successo.")
print(f" Ignorate {righe_ignorate} righe problematiche.")
