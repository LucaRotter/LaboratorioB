Viene fornito, ai fini della correzione, un tool per creare in automatico il database e le relative tabelle (cancellandoli se già presenti).

Per usufruirne, è sufficiente dare il comando "java -jar target/db-creator-1.0-SNAPSHOT-all.jar" nella cartella backend 
e successivamente inserire username del e password del database.
Le credenziale verranno chieste 2 volte: una per creare il database e una per creare le tabelle usando la connessione con HikariCP per la seconda.
Per popolare il database di Libri, è necessario usare il file "Dati_Libri.csv" presente nella cartella doc/database e il file convertitore.py

Poi per popolare il database bisogna: python convertitore.py e successivamente inserire username e password

ATTENZIONE:il file convertitore.py e Dati_Libri.csv devono essere nella stessa cartella.


