insert into docenten(voornaam, familienaam, wedde, emailadres, geslacht, campusid)
values('testM', 'testM', 1000, 'testM@test.be', 'MAN', (select id from campussen where naam = 'test'));
insert into docenten(voornaam, familienaam, wedde, emailadres, geslacht, campusid)
values('testV', 'testV', 1000, 'testV@test.be', 'VROUW', (select id from campussen where naam = 'test'));
insert into docentenbijnamen(docentid, bijnaam)
values((select id from docenten where voornaam = 'testM'), 'test');


/*insert into docenten(voornaam, familienaam, geslacht, wedde, emailadres)
values ('testM', 'testM', 'MAN', 1000, 'testM@test.be');
insert into docenten(voornaam, familienaam, geslacht, wedde, emailadres)
values ('testV', 'testV', 'VROUW', 1000, 'testV@test.be');
insert into docentenbijnamen(docentid, bijnaam)
values ((select id from docenten where voornaam = 'testM'), 'test');
*/
