# Fahrzeugmarkt
Autos und Motorräder kaufen und verkaufen. 

Jedes Objekt hat allgemeine Attribute (Marke, Modell, Jahr, Hubraum, PS, Drehmoment etc) aber auch Attribute spezifisch für ein Auto oder Motorrad (Anzahl Türe / Art der Federung etc)

Benutzer fallen in 3 Kategorien: Käufer, Verkäufer und Administratoren
Alle haben gemeinsame Attribute wie Benutzername, Passwort und Standort, aber auch spezifische.

Jeder Käufer kann sich alle verfügbare Anzeigen anschauen.
Wenn sie sich entscheiden, können sie ein Gebot abgeben oder die Anzeige direkt kaufen. (Eine Transaktion einsenden)

Die Verkäufer haben ein zusätzliches Attribut, Rating, der die Käufer sehen können. Diese können neue Autos auf dem Markt stellen oder löschen.
Falls ein Käufer eine Anzeige kauft oder die Auktion gewinnt, kann der Verkäufer diese Transaktion annehmen oder ablehnen. Falls eine Auktion zu Ende kommt und niemand darauf geboten hat, kann der Verkäufer entweder die Anzeige verlängern oder diese löschen.

Der Administrator kann neue Benützer erstellen oder bereits existierende Benützer löschen.

![Diagramm A](https://github.com/MAPUBB2022/Fahrzeugmarkt/blob/2295b52ced04c1556c88a2f494f1a1d17a6bb099/Diag_A.png)
![Diagramm B](https://github.com/MAPUBB2022/Fahrzeugmarkt/blob/2295b52ced04c1556c88a2f494f1a1d17a6bb099/Diag_B.png)
