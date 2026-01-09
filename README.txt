Viene fornito, ai fini della correzione, un tool per creare in automatico il database e le relative tabelle (cancellandoli se già presenti).

Per usufruirne, è sufficiente dare il comando "java -jar DBCreator-1.0-jar-with-dependencies.jar 
e successivamente inserire username del e password del database.
Le credenziale verranno chieste 2 volte: una per creare il database e una per creare le tabelle usando la connessione con HikariCP per la seconda.
Per popolare il database di Libri, è necessario usare il file "Dati_Libri.csv" presente nella cartella doc/database e il file convertitore.py

Poi per popolare il database bisogna: python convertitore.py e successivamente inserire username e password


