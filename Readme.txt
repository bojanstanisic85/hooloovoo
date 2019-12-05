Automatizacija REST Apija je radjena u Java programskom jeziku u kombinaciji TestNg frejmvorka i REST Assured DSL-a uz pomoc Hamcrest apija.
Testiranje je vrseno u data-driven maniru uz pomoc TestNg DataProvider klase.

U toku testiranja pronadjeni su sledeci nedostaci
1) registracija korisnika
	- Po specifikaciji aplikacija ne bi smela da prihvati "weak" password - a prihvata ga,  moguce je cak umesto password-a staviti prazan string.
	- Korisnicko ime bi trebalo da bude duzine izmedju 6 i 50 karaktera, ali je dozvoljeno izmedju 5 i 50.
2) provera logovanja na sistem
	- Prilikom logovanja sa nepostojecim korisnikom sistem vraca 417 sto nije u skladu sa dokumentacijom, ocekivano je 404. 
	- Isto se desava i prilikom logovanja sa postojecim korisnikom ali sa pogresnim passwordom.
3) provere postojanja korisnika
4) beleženja akcija korisnika
	- Prilikom dodavanja akcije korisnika ako unesemo nevalidan enum dobijamo 500 internal server error
	- Isto se desava prilikom pokusaja dodavanja akcije sa nepostojecim korisnikom
5) pregled akcija korisnika po korisničkom imenu
	- "createdAt" polje uvek ima vrednost null prilikom dohvatanja svih akcija jednog korisnika.
	- Ako se izostavi korisnik u API pozivu ili ga nema u bazi sistem vraca prazan niz kao response body i status 200OK.
	- Response body je uvek prazan niz - [] kada pokusamo request sa nevalidnim parametrima.
	
Produkciono okruzenje je delimicno simulirano paralelnim izvrsavanjem testova na nivou klasa sto je podeseno u TestNG.xml fajlu. 
Ovo se moglo postici i na nivou pojedinacnih testova setovanjem parametara DataProvider-a.
