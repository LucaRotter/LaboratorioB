import pandas as pd
import psycopg2
from psycopg2 import sql

csv_path = 'percorso/al/tuo/Dati_Libri.csv'
table_name = 'libri'
db_params  = {
    'dbname': 'labB',
    'user': 'postgres',
    'password': 'Rluca2004',
    'host': 'localhost',
    'port': 5432
}

df = pd.read_csv(csv_path)

df.insert(0, 'id', range(1, len(df) + 1))

conn = psycopg2.connect(**db_params)
cur = conn.cursor()
    

# 4️⃣ Copia i dati in blocco nel database
# Metodo efficiente con StringIO
'''from io import StringIO
buffer = StringIO()
df.to_csv(buffer, index=False, header=False, sep=";")
buffer.seek(0)

columns = ','.join(df.columns)
cur.copy_expert(f"COPY {table_name} ({columns}) FROM STDIN WITH (FORMAT csv, DELIMITER ';', NULL '')", buffer)
conn.commit()'''

for i, row in df.iterrows():
    insert_query = sql.SQL("INSERT INTO libri (id_libro, titolo, autore, anno, genere, editore) VALUES (?, ?, ?, ?, ?, ?)",
                            [row[0], row[1], row[2], row[5], row[3], row[4]])
    cur.execute(insert_query)

# 5️⃣ Chiude connessione
cur.close()
conn.close()
print(f"✅ Dataset '{csv_path}' caricato con successo nella tabella '{table_name}'")