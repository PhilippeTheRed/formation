Mettre en oeuvre JPA 
avec l'implémentation Hibernate

Persistez, persistez, il en restera toujours 
quelque chose...

(vous avez encore quelques secondes pour fuir…)

Plan

Mapper les attributs simples

Réaliser les opérations CRUD

Mapper l’héritage

Mapper les associations

Utiliser le LazyLoading

Réaliser des requêtes de chargement 

complexe avec le JPQL

Réaliser des requêtes de chargement 

complexe avec Criteria


---

2

12.03.25

Configurer JPA/Hibernate

Introduction

Gérer le lien entre les données et les 

objets est une tâche délicate !

Complexité technique

●Gestion des connexions

●Fuites mémoires

●Passage d'un modèle à l'autre

●Gestion des clefs étrangères…

Coût important


---

3

12.03.25

Avant les ORMs

Mapping « à la main » des objets « BDD » 

vers les objets « Java »

Risque d'erreur important

Difficulté à parcourir le graphe

Coût important de maintenance en cas 

d'évolution du modèle

Travail « ingrat »


---

4

12.03.25

Exemple

public Medecin chargeMedecin(long medecinId, Connection cnx) {

if (0 == medecinId || cnx == null) { return null; } 
PreparedStatement stmt = null;
try {

// préparation de la requête à la base de données
stmt = cnx.prepareStatement("SELECT ID, NOM, TELEPHONE FROM MEDECIN WHERE ID = ?");
stmt.setLong(1, customerId);
ResultSet rs = stmt.executeQuery();
if (rs.next()) {

// transformation des données relationnelles en objet Java "Medecin"
Medecin medecin = new Medecin();
int index = 1;
medecin.setId(rs.getLong(index++));
medecin.setNom(rs.getString(index++));
medecin.setTelephone(rs.getString(index++));
return medecin;

}

} catch (final Exception e) {

log.info("", e);

} finally {

if (stmt != null) {
try {

stmt.close();

} catch (final SQLException e) {


---

5

log.info("", e);

}

}

12.03.25

Exemple

Constitution du PrepareStatement

public Medecin chargeMedecin(long medecinId, Connection cnx) {

if (0 == medecinId || cnx == null) { return null; } 
PreparedStatement stmt = null;
try {

// préparation de la requête à la base de données
stmt = cnx.prepareStatement("SELECT ID, NOM, TELEPHONE FROM MEDECIN WHERE ID = ?");
stmt.setLong(1, customerId);
ResultSet rs = stmt.executeQuery();
if (rs.next()) {

// transformation des données relationnelles en objet "Medecin"
Medecin medecin = new Medecin();
int index = 1;
medecin.setId(rs.getLong(index++));
medecin.setNom(rs.getString(index++));
medecin.setTelephone(rs.getString(index++));
return medecin;

}

} catch (final Exception e) {

log.info("", e);

} finally {

if (stmt != null) {
try {

stmt.close();

} catch (final SQLException e) {


---

6

log.info("", e);

}

}

12.03.25

Exemple

Constitution du PrepareStatement

public Medecin chargeMedecin(long medecinId, Connection cnx) {

if (0 == medecinId || cnx == null) { return null; } 
PreparedStatement stmt = null;
try {

// préparation de la requête à la base de données
stmt = cnx.prepareStatement("SELECT ID, NOM, TELEPHONE FROM MEDECIN WHERE ID = ?");
stmt.setLong(1, customerId);
ResultSet rs = stmt.executeQuery();
if (rs.next()) {

Parcours des résultats et
constitution des objets Java

// transformation des données relationnelles en objet "Medecin"
Medecin medecin = new Medecin();
int index = 1;
medecin.setId(rs.getLong(index++));
medecin.setNom(rs.getString(index++));
medecin.setTelephone(rs.getString(index++));
return medecin;

}

} catch (final Exception e) {

log.info("", e);

} finally {

if (stmt != null) {
try {

stmt.close();

} catch (final SQLException e) {


---

7

log.info("", e);

}

}

12.03.25

Exemple

Constitution du PrepareStatement

public Medecin chargeMedecin(long medecinId, Connection cnx) {

if (0 == medecinId || cnx == null) { return null; } 
PreparedStatement stmt = null;
try {

// préparation de la requête à la base de données
stmt = cnx.prepareStatement("SELECT ID, NOM, TELEPHONE FROM MEDECIN WHERE ID = ?");
stmt.setLong(1, customerId);
ResultSet rs = stmt.executeQuery();
if (rs.next()) {

Parcours des résultats et
constitution des objets Java

// transformation des données relationnelles en objet "Medecin"
Medecin medecin = new Medecin();
int index = 1;
medecin.setId(rs.getLong(index++));
medecin.setNom(rs.getString(index++));
medecin.setTelephone(rs.getString(index++));
return medecin;

Fermeture PrepareStatement

}

} catch (final Exception e) {

log.info("", e);

} finally {

if (stmt != null) {
try {

stmt.close();

} catch (final SQLException e) {


---

8

log.info("", e);

}

}

12.03.25

Équivalent avec JPA/Hibernate

public Medecin chargeMedecin(long medecinId) {

TypedQuery query =

entityManager.createQuery(« SELECT med FROM Medecin med where id 

=:id ») ;

query.setParameter(« id », medecinId) ;

return query.getSingleResult();

}


---

9

Et voilà !...

12.03.25

Équivalent avec JPA/Hibernate

Ou encore plus simple :

public Medecin chargeMedecin(long medecinId) {

return entityManager.findById(medecinId, Medecin.class);

}


---

10

Et voilà !...

12.03.25

Équivalent avec JPA/Hibernate

ET ENCORE PLUS SIMPLE
DANS SA VERSION LA PLUS RÉCENTE,
COUPLÉ AVEC SPRING :

public interface MedecinRepository extends JpaRepository{}

(Permet de récupérer 1 ou tous les médecins par leur identifiant
, de les supprimer, de les mettre à jour etc.)


---

11

12.03.25

Naissance d'Hibernate

Gaving King, développeur principal, début 


---

2001

Produire un outil qui prenne en charge le lien 

entre BDD et objet Java

Concentration sur le modèle objet Java

Lien « organique » entre modèle Java et 

modèle BDD


---

12

12.03.25

Naissance de la norme JPA

Java Persistence API, version 1.0 en 2006

Permet de limiter la dépendance à une 

implémentation

Hibernate reste de loin l'implémentation la 

plus utilisée, nombreuses fonctionnalités 
supplémentaires

Aujourd'hui JPA version 2.1, Hibernate 

version 5.9 pour la version XML

Hibernate 6.1 pour la version Spring-Boot 3

12.03.25


---

13

Critique envers les ORMs

Certains développeurs critiquent la trop 

grande complexité d'Hibernate

Aspect « boîte noire »

Caractère « Statefull »

Nécessité de comprendre la mécanique

… Mais comment c'était avant ??!


---

14

12.03.25

TP 1 : Avant les ORMs

Créer un schéma test

Restaurer le jeu de données fourni :

● Executer le script dans src/test/resources/tp1

Créer la méthode de récupération de toutes 

les entreprises triées par date de création 
dans EntrepriseDAOJDBCImpl

Tester avec EntrepriseDAOJDBCTest

En cas de problème d’exécution avec la 

version de Junit, faire « Run as → Run 
Configuration » et sélectionner la bonne 
version (Junit 5 pour version Spring-Boot 3)

12.03.25


---

15

Conclusion

Retour arrière difficilement envisageable

Considérer Hibernate/JPA d’emblée comme 

un framework « lourd »

Coût d'entrée important, mais accepter de 

l'assumer


---

16

12.03.25

Mapping des

attributs simples


---

17

12.03.25

Le mapping des attributs simples

On va d’abord voir comment mapper un objet 

avec des attributs simples

Pas de liens entre les objets pour l’instant

Correspond à une seule table en BDD, sans 

clefs étrangères


---

18

12.03.25

Principes de cette formation

Versions utilisées pour la formation (avec et 

sans Spring Boot) :

●Hibernate 5.9 ou 6.1
●Spring 4.3 ou 6.0

Les partie « Conseil : » sont personnels, 

certains développeurs sûrement d’avis 
différents

Tentative de montrer aussi « Comment 
utiliser Hibernate », car framework riche et 
parfois on s’y perd

12.03.25


---

19

Les principes d'Hibernate/JPA

Se concentrer sur le modèle objet Java

Ajout de métadonnées sur ce modèle

Lien « organique » entre modèle Java et 

modèle relationnel

Génération du schéma de la base → la 
structure de la BDD « découle » des objets 
Java + config d’Hibernate


---

20

12.03.25

XML vs Annotations

À l'origine, le « mapping » est réalisé avec 

des fichiers XML

Depuis évolution vers un mapping via les 

annotations

Mapping directement intégré au code des 

classes métier

Utilisation des annotations meilleure garantie 

pour la correction des bugs


---

21

12.03.25

Mise en place du mapping

On utilise @Entity pour déclarer qu'une 

classe métier représente une table

La classe doit contenir :

● Un constructeur vide (présent par 

défaut)

● Un identifiant (expliqué plus loin)

Par défaut nom de table = nom de la classe

Conseil : Conserver le comportement par 

défaut autant que possible


---

22

Permet d’avoir une cohérence :

12.03.25

La déclaration des identifiants

Une « entité » doit définir un identifiant

Correspond au besoin d’une clef primaire 

pour une table en BDD

Conseil : Préférer les identifiants techniques 

(règles métier peuvent évoluer)

Se déclare avec @Id

Possibilité de déclarer des identifiants 

composites (pas conseillé) avec :

● @EmbeddedId (expliqué plus loin)


---

23

● @IdClass

12.03.25

La déclaration des attributs/colonnes (1/2)

Par défaut : Nom de colonne = nom de 

l'attribut

Le CamelCase devient → camel\_case 

(underscore)

Tous les types basiques sont mappés par 
défaut vers les types BDD correspondants

Exemples :

● java.lang.String → VARCHAR

● java.lang.Boolean → BOOLEAN


---

24

● java.util.Date → TIMESTAMP …

12.03.25

Avec @Column, possibilité de préciser le 

La déclaration des attributs/colonnes (2/2)

Pour les dates, possibilités de choix du type 

plus fin avec @Temporal

Les enums doivent être annoté avec 

@Enumerated avec au choix :

● EnumType.STRING : Les valeurs stockées 
correspondent au nom des valeurs d'enum 
(préférence perso car plus parlant)

● EnumType.ORDINAL: Les valeurs stockées 

correspondent à une numérotation des valeurs de 
l'énum (problème si ajout de nouvelles valeurs 
d’enum)

Tous les types complexes nécessitent un 


---

25

mapping plus élaboré

12.03.25

Exemple

@Entity
public class Medecin {

@Id 
private Integer id;

private String nom;

public Medecin() {
}

public Integer getId() { return id;}

public void setId(Integer id) { this.id = id;}

public String getNom() { return nom; }

public void setNom(String nom) { this.nom = nom;}

}


---

26

12.03.25

Exemple

@Entity
public class Medecin {

@Id 
private Integer id;

private String nom;

public Medecin() {
}

Table correspondante

public Integer getId() { return id;}

public void setId(Integer id) { this.id = id;}

public String getNom() { return nom; }

public void setNom(String nom) { this.nom = nom;}

}


---

27

MEDECIN

ID integer 
(primary key)

NOM 
varchar(255)

12.03.25

Exemple

@Entity
public class Medecin {

@Id 
private Integer id;

private String nom;

public Medecin() {
}

Table correspondante

public Integer getId() { return id;}

public void setId(Integer id) { this.id = id;}

public String getNom() { return nom; }

public void setNom(String nom) { this.nom = nom;}

}


---

28

MEDECIN

ID integer 
(primary key)

NOM
varchar(255)

12.03.25

Exemple

@Entity
public class Medecin {

@Id 
private Integer id;

private String nom;

public Medecin() {
}

Table correspondante

public Integer getId() { return id;}

public void setId(Integer id) { this.id = id;}

public String getNom() { return nom; }

public void setNom(String nom) { this.nom = nom;}

}


---

29

MEDECIN

ID integer 
(primary key)

NOM
varchar(255)

12.03.25

Exemple

@Entity
public class Medecin {

@Id 
private Integer id;

private String nom;

public Medecin() {
}

Table correspondante

public Integer getId() { return id;}

public void setId(Integer id) { this.id = id;}

public String getNom() { return nom; }

public void setNom(String nom) { this.nom = nom;}

}


---

30

MEDECIN

ID integer 
(primary key)

NOM
varchar(255)

12.03.25

Exemple

@Entity
public class Medecin {

@Id 
private Integer id;

Table correspondante

@Column(name = "nom\_med", length=50, nullable=false)
private String nom;

public Medecin() {
}

public Integer getId() { return id;}

public void setId(Integer id) { this.id = id;}

public String getNom() { return nom; }

public void setNom(String nom) { this.nom = nom;}

}


---

31

MEDECIN

ID integer 
(primary key)

NOM\_MED
varchar(50)
not null

12.03.25

La génération des valeurs d'identifiants

Peut-être gérée en dehors d'Hibernate 

(déconseillé) → Pas de déclaration

JPA/Hibernate a un effet « aspirant » : Plus on 

l’utilise, plus il faut l’utiliser

Préférable de ne pas « mixer » les 

technologies

La génération de valeurs d’identifiant via 
Hibernate se déclare avec @GeneratedValue


---

32

12.03.25

La génération des valeurs d'identifiants

Par défaut @GeneratedValue en place une 

séquence en BDD (avec Postgres)

Le nom est déterminé par l’entité

Ex : Medecin → medecin\_seq

Increment positionné à 50 par défaut 

(important cf diapos suivantes)

Convient bien mais attention à la dépendance 

au nom de l’objet


---

33

12.03.25

Choix d'une « stratégie »

4 possibilités pour générer les identifiants via 

Hibernate

SEQUENCE : utilisation d'une séquence avec 

@Sequence Generator

TABLE : utilisation d'une table avec 

@TableGenerator

IDENTITY : on se repose sur un mécanisme 

inhérent à la base (type SERIAL pour 
Postgres)

AUTO : on laisse Hibernate choisir 


---

34

(déconseillé), par défaut séquence sous 
Postgres

12.03.25

Exemple : Utilisation des séquences

@Id

@SequenceGenerator(name = "SEQUENCE\_GEN", 
sequenceName="hibernate\_sequence",allocationSize = 
20)

@GeneratedValue(strategy = GenerationType.SEQUENCE, 
generator = "SEQUENCE\_GEN")

private Integer id;

Importance du paramètre allocationSize pour 

les performances :

→ Met en œuvre un algorythme type « Hilo » 


---

35

pour attribuer plusieurs identifiants avec un 
seul appel à la séquence

12.03.25

Le cas des UUID

Identifiants générés par algorithme qui 

garantit l'unicité à un epsilon près

Intéressant pour des problématiques de 

distribution

Mais peut être volumineux en BDD (16 octets 

au min)

Utilisation d'une annotation spécifique 

d'hibernate @UuidGenerator :

@Id


---

36

@GeneratedValue
@UuidGenerator

12.03.25

Les attributs Embedded (imbriqués)

Regroupement de plusieurs colonnes dans 

un même objet

On regroupe plusieurs attributs qui forment 

un tout cohérent

Mapping identique aux entités, mais sans Id

Conséquence : pas de requête directe 

possible

Utile pour des objets qui n'ont pas de vie 

indépendante d'un objet-conteneur

Exemple type : l'adresse de l'utilisateur → 

12.03.25


---

37

Notion propre à l'utilisateur

Les attributs Embedded (imbriqués)

Se déclare dans l'objet contenant avec 

@Embedded

L'objet « imbriqué » est lui annoté avec 

@Embeddable (« imbriquable »)

Si réutilisation, surcharge possible du 

mapping avec @AssociationOverride 
(modification des noms de colonnes, des 
types de stockage etc.)

Possibilité de définir des Collections 


---

38

d'attributs embedded avec 
@CollectionElement (on a alors une table sans 
clef primaire)

12.03.25

Table correspondante

Exemple

@Entity
public class Medecin {

@Id 
private Integer id;

private String nom;

@Embedded 
private Adresse adresse;

…

}

@Embeddable
public class Adresse {

@Column(name = "num", length=3)
private String numero;

private String nomRue;

…

}


---

39

MEDECIN

ID integer 
(primary key)

NOM
varchar(255)

NUM
varchar(3)

NOMRUE
varchar(255)

12.03.25

Table correspondante

Exemple

@Entity
public class Medecin {

@Id 
private Integer id;

private String nom;

@Embedded 
private Adresse adresse;

…

}

@Embeddable
public class Adresse {

@Column(name = "num", length=3)
private String numero;

private String nomRue;

…

}


---

40

MEDECIN

ID integer 
(primary key)

NOM
varchar(255)

NUM
varchar(3)

NOMRUE
varchar(255)

12.03.25

Table correspondante

Exemple

@Entity
public class Medecin {

@Id 
private Integer id;

private String nom;

@Embedded 
private Adresse adresse;

…

}

@Embeddable
public class Adresse {

@Column(name = "num", length=3)
private String numero;

private String nomRue;

…

}


---

41

MEDECIN

ID integer 
(primary key)

NOM
varchar(255)

NUM
varchar(3)

NOMRUE
varchar(255)

12.03.25

Le mapping des dates

Il est fortement conseillé d’utiliser les dates 

de la nouvelles API java.time

On utilisera donc les types (type 

correspondant en BDD) :

● LocalDate → pour une date sans heure 

(DATE)

● LocalTime → pour une heure sans date 

(TIME)

● LocalDateTime → date et heure 

(TIMESTAMP)


---

42

● ZonedDateTime et OffsetDateTime → 

12.03.25

date et heure avec fuseau horaire 

Les Converter

Pour les attributs non-basiques mais peu 
complexes, qui ne correspondent pas à un 
objet métier (ex : Year, YearMonth, ou vos 
propres classes… )

Possibilité de les mapper en définissant la 
façon de les convertir en valeur pour la BDD

On définit une classe annotée @Converter 
qui implémente AttributeConverter et ses 2 
méthodes :

● ConvertToDatabaseColumn


---

43

● ConvertToEntityAttribute

12.03.25

Possibilité aussi de définir ou réutiliser des 

Les Converter : exemple

@Converter(autoApply = true)

public class YearMonthConverter implements AttributeConverter {

@Override

public LocalDate convertToDatabaseColumn(YearMonth yearMonth) {

return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);

}

@Override

public YearMonth convertToEntityAttribute(LocalDate localDate) {

return YearMonth.from(localDate);

}

}


---

44

12.03.25

TP 2 : Mapping des attributs simples

Lire le script de création du schéma fourni

Mapper les classes Entreprise, Declaration, 

Secteur pour obtenir un schéma identique

Lancer TestGenerationScript pour générer 

votre script et vérifier qu'il correspond

Le script est généré à la racine du projet, 

nommé create.sql

Attention supprimer le script avant de 

relancer sinon les commandes sql 
s’accumulent


---

45

12.03.25

Réaliser les

opérations CRUD


---

46

12.03.25

Les opérations CRUD via JPA

On a vu comment lier une classe, à son 

équivalent dans la BDD

On va voir maintenant comment effectuer les 

opérations de base sur cette table

JPA permet d’effectuer toutes ces opérations 

en langage-objet, sans écrire de SQL


---

47

12.03.25

Les opérations CRUD via JPA

Principe de JPA : les opérations CRUD se 

font à travers les objets Java

JPA offre un ensemble de classes et 
méthodes permettant de réaliser ces 
opérations basiques

Couche d'abstraction entre le code métier et 

la persistance des données

Effet aspirant : Il vaudra mieux ensuite passer 

au maximum par les mécanismes de JPA


---

48

12.03.25

EntityManager : classe centrale de JPA

Classe par qui s'effectue l'ensemble des 

opérations JPA

Méthodes basiques:

● public void persist(Object entity) : Permet de rendre 
persistant un objet nouvellement créé en Java → 
traduction SQL : INSERT

● public  T find(Class entityClass, Object 

primaryKey) : Permet de récupérer un objet via son 
identifiant depuis la base → traduction SQL : SELECT. 
La classe de l’entité correspondante doit être 
spécifiée


---

49

● public void remove(Object entity) : Permet de 

12.03.25

supprimer un objet persistant → traduction SQL : 
DELETE

Principe de fonctionnement classique

JPA tâche d'être le moins présent possible au 

sein du code métier

Récupération des objets via l'EntityManager

Modifications directement faites sur les 

objets Java et leurs attributs

À la fin de la transaction : l'EntityManager 

« envoie » l'ensemble de ces modifications à la 
base, via des instructions SQL

On appelle cette opération le flush()

12.03.25


---

50

Schéma du fonctionnement classique

Récupération des objets
depuis la BDD

EntityManager.find(…)
(souvent au travers des DAOs)

Transaction

Traitement sur les objets
récupérés

Modification des objets
via leurs setters
EntityManager.persist(…)
EntityManager.remove(…)
(si insertion ou suppression)

Fin de transaction

EntityManager.flush()
(envoi des instructions SQL à la base
et commit)


---

51

12.03.25

Exemple

entityManager.getTransaction().begin(); (*)

Medecin medecin = entityManager.find(Medecin.class, 1);

Récupération du
Médecin d’identifiant 1

medecin.setTelephone("0123456789");

Patient patient = medecin.getPatients().get(0);

entityManager.remove(patient);

entityManager.getTransaction().commit(); (*)

Traitement sur
les objets

Fin de transaction → flush :
UPDATE telephone FROM medecin…
DELETE FROM patient…

(*) : Les lignes suivies d’une astérix sont là uniquement pour faire 
apparaître les transactions, dans la réalité il s’agit d’opérations 
transparentes


---

52

12.03.25

Pattern « Unit of Work »

Pour les UPDATES, on voit qu'il n'est pas 

nécessaire de spécifier explicitement à JPA les 
objets à mettre à jour

Par défaut, JPA considère que toute 

modification sur un objet qu'il « gère » (état 
managed : notion vue plus loin) doit être 
persistée

Corresponds à la grande majorité des cas

Sinon il faut explicitement « détacher » l'objet 


---

53

de l'EntityManager avec 
entityManager.detach(objet)

12.03.25

TP 3 : Réaliser les opérations CRUD

Définir EntrepriseDAOJPAImpl comme 

implémentation de EntrepriseDAO

Compléter les méthodes de la classe 

EntrepriseDAOJPAImpl

Tester avec EntrepriseDAOCRUDTest


---

54

12.03.25

Mapper les relations

d’héritage


---

55

12.03.25

Mapping de l'héritage

On a vu comment mapper une classe (sans 

lien entre les classes)

Puis comment réaliser les opérations de base 

sur cette classe

On va maintenant s’attaquer à une notion 

importante en Java : l’héritage

On va voir comment traduire les relations 

d’héritage en BDD via le mapping JPA


---

56

12.03.25

Mapping de l'héritage

JPA offre plusieurs possibilités pour mapper 

les relations d'héritage

Au préalable se poser la question : Doit-on 

utiliser l'héritage ? 
(https://www.youtube.com/watch?v=wfMtDGfHWpA)

4 grandes « stratégies » proposées par 

Hibernate

Questions à se poser pour choisir :

● Vais-je utiliser le polymorphisme ? (requête 

directement sur la classe mère)


---

57

● Quelle est la volumétrie ? (performances)

12.03.25

Mapping de l'héritage

4 grandes « stratégies » :

@MappedSuperclass

@Inheritance strategy = JOINED

@Inheritance strategy = SINGLE\_TABLE

@Inheritance strategy = TABLE\_PER\_CLASS


---

58

12.03.25

@MappedSuperclass

Permet seulement la mise en commun 
d'attribut → pas de requête polymorphique

Se déclare au niveau de la (ou des) classe 

mère

Les attributs s'ajoutent dans les tables des 

entités filles

Les attributs hérités sont pris en compte par 

JPA


---

59

12.03.25

@MappedSuperclass

@MappedSuperclass
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

60

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@MappedSuperclass

RECTANGLE

CARRE

polygoneId

nbCotes

longueur

largeur

polygoneId

nbCotes

tailleCote


---

1


---

2


---

4


---

4

10,5

2,5

6,5


---

8


---

3


---

4


---

4


---

4

10,5

2,5

@MappedSuperclass
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

61

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@MappedSuperclass

RECTANGLE

CARRE

polygoneId

nbCotes

longueur

largeur

polygoneId

nbCotes

tailleCote


---

1


---

2


---

4


---

4

10,5

2,5

6,5


---

8


---

3


---

4


---

4


---

4

10,5

2,5

@MappedSuperclass
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

62

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@MappedSuperclass

RECTANGLE

CARRE

polygoneId

nbCotes

longueur

largeur

polygoneId

nbCotes

tailleCote


---

1


---

2


---

4


---

4

10,5

2,5

6,5


---

8


---

3


---

4


---

4


---

4

10,5

2,5

@MappedSuperclass
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

63

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

Les héritages de type @Inheritance

Tous les autres type de mapping de l'héritage 
permettent les requêtes sur les classes mères 
(ie polymorphiques)

Différence au niveau du modèle en base de 

données

Différence au niveau des requêtes 

polymorphiques générées

Arbitrage entre performances et 

normalisation


---

64

12.03.25

@Inheritance stratégie SINGLE\_TABLE

Toutes les implémentations sont stockées 

dans la même table (stratégie par défaut)

Utilisation d'une colonne (DTYPE par défaut) 

comme discriminant pour connaître l’entité 
correspondante (possible redéfinition avec 
@DiscriminatorColumn)

Désavantage : Nombre de colonnes plus 

important car des colonnes à null :

● Schéma dénormalisé

● Perte d'espace


---

65

Avantage : Les requêtes polymorphiques 

n'utilisent qu'une table, pas de jointure

12.03.25

@Inheritance stratégie SINGLE\_TABLE

POLYGONE

polygoneId


---

1


---

2


---

3

DTYPE

Rectangle

Rectangle

Carre

nbCotes

longueur

largeur

tailleCote


---

4


---

4


---

4

10,5

2,5

null

6,5


---

8

null

null

null

3,5

@Entity 
@Inheritance(strategy=InheritanceType.SINGLE\_TABLE)
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

66

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@Inheritance stratégie SINGLE\_TABLE

POLYGONE

polygoneId


---

1


---

2


---

3

DTYPE

Rectangle

Rectangle

Carre

nbCotes

longueur

largeur

tailleCote


---

4


---

4


---

4

10,5

2,5

null

6,5


---

8

null

null

null

3,5

@Entity 
@Inheritance(strategy=InheritanceType.SINGLE\_TABLE)
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

67

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@Inheritance stratégie SINGLE\_TABLE

POLYGONE

polygoneId


---

1


---

2


---

3

DTYPE

Rectangle

Rectangle

Carre

nbCotes

longueur

largeur

tailleCote


---

4


---

4


---

4

10,5

2,5

null

6,5


---

8

null

null

null

3,5

@Entity 
@Inheritance(strategy=InheritanceType.SINGLE\_TABLE)
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

68

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@Inheritance stratégie JOINED

Chaque classe est mappée vers une table, y 

compris la classe mère

La clef primaire des sous-classes, est aussi 

une clef étrangère vers la ligne de la classe 
mère

Requête polymorphique réalisée à l'aide 

d'une jointure

Schéma normalisé →

Désavantage : multiplication des jointures


---

69

→ avec un volume important, les jointures 

peuvent être coûteuses en perfs

12.03.25

@Inheritance stratégie JOINED

POLYGONE

polygoneId

nbCotes


---

1


---

2


---

3


---

4


---

4


---

4


---

4


---

4

CARRE

polygoneId

tailleCote


---

3
@Entity 
@Inheritance(strategy=InheritanceType.JOINED)

---

4
public class Polygone { 

10,5

2,5

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

RECTANGLE

polygoneId

longueur

largeur


---

1


---

2

10,5

2,5

6,5


---

8

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

70

@Inheritance stratégie JOINED

POLYGONE

polygoneId

nbCotes


---

1


---

2


---

3


---

4


---

4


---

4


---

4


---

4

CARRE

polygoneId

tailleCote


---

3
@Entity 
@Inheritance(strategy=InheritanceType.JOINED)

---

4
public class Polygone { 

10,5

2,5

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

RECTANGLE

polygoneId

longueur

largeur


---

1


---

2

10,5

2,5

6,5


---

8

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

71

@Inheritance stratégie JOINED

POLYGONE

polygoneId

nbCotes


---

1


---

2


---

3


---

4


---

4


---

4


---

4


---

4

CARRE

polygoneId

tailleCote


---

3
@Entity 
@Inheritance(strategy=InheritanceType.JOINED)

---

4
public class Polygone { 

10,5

2,5

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

RECTANGLE

polygoneId

longueur

largeur


---

1


---

2

10,5

2,5

6,5


---

8

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

72

@Inheritance stratégie JOINED

RECTANGLE

polygoneId

longueur

largeur

POLYGONE

polygoneId

nbCotes


---

1


---

2


---

3


---

4


---

4


---

4


---

4


---

4

CARRE

polygoneId

tailleCote


---

1


---

2

10,5

2,5

6,5


---

8

@Entity 
@Inheritance(strategy=InheritanceType.JOINED)
public class Polygone { 


---

3


---

4

10,5

2,5

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

73

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@Inheritance stratégie TABLE\_PER\_CLASS

Aussi connue sous le nom (plus parlant) de 

table-per-concrete-class

Identique à @MappedSuperclass, mais 

autorise les requêtes polymorphiques

Une table par classe concrète est créée 
contenant les attributs de la classe mère et 
ceux hérités

Légère dé-normalisation → les colonnes 

communes sont présentes dans chaque table

Pas de jointure pour récupérer les objets des 

12.03.25

classes filles


---

74

@Inheritance stratégie TABLE\_PER\_CLASS

RECTANGLE

CARRE

polygoneId

nbCotes

longueur

largeur

polygoneId

nbCotes

tailleCote


---

1


---

2


---

4


---

4

10,5

2,5

6,5


---

8


---

3


---

4


---

4


---

4

10,5

2,5

@Entity 
@Inheritance(strategy=TABLE\_PER\_CLASS)
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

75

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@Inheritance stratégie TABLE\_PER\_CLASS

RECTANGLE

CARRE

polygoneId

nbCotes

longueur

largeur

polygoneId

nbCotes

tailleCote


---

1


---

2


---

4


---

4

10,5

2,5


---

4


---

4

10,5

2,5

6,5


---

3


---

8
@Entity 
@Inheritance(strategy=TABLE\_PER\_CLASS)
public class Polygone { 


---

4

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

76

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@Inheritance stratégie TABLE\_PER\_CLASS

RECTANGLE

CARRE

polygoneId

nbCotes

longueur

largeur

polygoneId

nbCotes

tailleCote


---

1


---

2


---

4


---

4

10,5

2,5

6,5


---

8


---

3


---

4


---

4


---

4

10,5

2,5

@Entity 
@Inheritance(strategy=TABLE\_PER\_CLASS)
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

77

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

}

12.03.25

@Inheritance stratégie TABLE\_PER\_CLASS

RECTANGLE

CARRE

polygoneId

nbCotes

longueur

largeur

polygoneId

nbCotes

tailleCote


---

1


---

2


---

4


---

4

10,5

2,5

6,5


---

8


---

3


---

4


---

4


---

4

10,5

2,5

@Entity 
@Inheritance(strategy=TABLE\_PER\_CLASS)
public class Polygone { 

@Id 
private Integer polygoneId ;

private Integer nbCotes ;

@Entity 
public class Rectangle extends Polygone { 

private Double longueur; 

private Double largeur;

}


---

78

@Entity 
public class Carre extends Polygone { 

private Double tailleCote ;

Requête sur la classe Polygone :

}

SELECT * FROM (

SELECT * FROM RECTANGLE

UNION ALL

SELECT * FROM CARRE

)

12.03.25

TP 4 : Mapper l'héritage

Avec le script fourni, deviner le mode d'héritage des 

classes d'indices qui a été choisi

Réaliser le mapping de la classe Indice et de ses 
filles IndiceMensuel et IndiceAnnuel avec ce mode 
(Rappel : on peut utiliser les converter cf diapo 41)

Vérifier la correspondance du script avec 

TestGenerationScript.java

Tester avec IndiceDAOTest


---

79

12.03.25

Mapping des

associations


---

80

12.03.25

Mapper les associations

On a vu comment mapper et manipuler les 

classes simples

Comment mapper les relations d’héritage 

entre les classes

On va voir maintenant un point essentiel : la 

gestion des liens entre les classes

C’est souvent ici qu’on trouve des pratiques 

pas très orthodoxes


---

81

12.03.25

Mapper les associations

Point très important pour une gestion 

optimale

JPA contraint à de bonnes pratiques pour les 

associations → Et c’est tant mieux !

Conseil : Respecter la logique objet

→ Pas d'attributs - clefs étrangères !!!

Matérialiser au maximum les liens objets

Règle générale : Modèle objet « propre »

→ Utilisation d'Hibernate facilitée


---

82

JPA va permettre de « naviguer » dans le 
graphe objet sans se préoccuper des accès 

12.03.25

Mapper les associations

JPA couvre toutes les associations possibles 

en termes de cardinalité

Certaines limites pour des associations avec 

polymorphisme

Avant tout, bien définir les cardinalités de 

l'assocation

Point important : Bien choisir le type de 
Collection pour les cardinalités multiples


---

83

12.03.25

Mapper les associations

Types d'associations possibles :

● @OneToMany : Relation (1 - *)

● @ManyToOne : Relation (* - 1)

● @OneToOne : Relation (1 - 1)

● @ManyToMany : Relation (* - * )

● @Any, @ManyToAny : Utilisée pour le 

polymorphisme (pas abordée ici)

Premier mot : Se réfère à « la classe où je 


---

84

suis »

12.03.25

Second mot : concerne l'autre bout de la 

relation, « la classe vers laquelle l’assocation 

Choisir le type de Collection adéquat

Question à se poser : quelles règles ma 

Collection devra respecter ?

Collection d'objets uniques → Utiliser les 

Set !

Collection ordonnée mais sans index → 

SortedSet

Ne pas utiliser systématiquement les List, 

seulement si réel besoin d'un index

Les Set permettent de garantir l'unicité → 
Utile à Hibernate pour constituer les objets 
issus des requêtes

12.03.25


---

85

Assocation @OneToMany (1 - *)

Medecin


---

1

n

Patient

Graphe Objet

En BDD

public class Medecin {

@OneToMany
@JoinColumn(name="idMedecin")
private Set patients 


---

1

public class Patient {

n

= new HashSet<>();
}

Code

12.03.25

}


---

86

Assocation @OneToMany (1 - *)

Medecin


---

1

n

Patient

Graphe Objet

En BDD

public class Medecin {

@OneToMany
@JoinColumn(name="idMedecin")
private Set patients 


---

1

public class Patient {

n

= new HashSet<>();
}

Code

12.03.25

}


---

87

Assocation @ManyToOne (* - 1)

Medecin


---

1

n

Patient

Graphe Objet

public class Medecin {


---

1

n

}


---

88

public class Patient {

@ManyToOne
@JoinColumn(name="idMedecin")
private Medecin medecin;

}

En BDD

Code

12.03.25

Assocation @OneToMany (1 - *)

L'attribut doit donc être une Collection

Par défaut, JPA génère une table 

d'association (pourquoi ?)

Il faut ajouter @JoinColumn pour que JPA 

gère l'association avec une clef étrangère

Le nom de la colonne est précisé dans son 

attribut « name »

On peut préciser avec @ForeignKey le nom 
de la contrainte clef étrangère, son unicité etc.


---

89

12.03.25

Assocation @ManyToOne (* - 1)

Relation exactement inverse, on change le 

« propriétaire »

On définit de même la jointure avec 

@JoinColumn

Le sens de navigation à travers le graphe 

objet change

La représentation en BDD est la même

C’est la matérialisation côté Java qui change


---

90

12.03.25

Sens de navigation et relation bidirectionnelle

Dans chaque exemple, 1 seul sens de 
navigation possible → Bien choisir dès le 
départ !!!

Il est possible de définir des relations 

« bidirectionnelles »

Débat sur utilisation de relations 

unidirectionnelles ou bidirectionnelles

Problème de gestion de la cohérence entre 

les 2 extrémités


---

91

Débat sur la gestion des associations :

12.03.25

● Certains développeurs conseillent 

d'éviter le bidirectionnelle

Relation bidirectionnelle : @OneToMany @ManyToOne

Medecin


---

1

n

Patient

Graphe Objet

En BDD

public class Medecin {

public class Patient {


---

1

n

@OneToMany(mappedBy="medecin")
private Set patients = new HashSet<>();

@ManyToOne
@JoinColumn(name="idMedecin")
private Medecin medecin;

Code

}


---

92

}

12.03.25

Relation bidirectionnelle : @OneToMany @ManyToOne

Medecin


---

1

n

Patient

Graphe Objet

En BDD

public class Medecin {

public class Patient {


---

1

n

@OneToMany(mappedBy="medecin")
private Set patients = new HashSet<>();

@ManyToOne
@JoinColumn(name="idMedecin")
private Medecin medecin;

}

Code

}


---

93

12.03.25

Finalement...

… Faites votre expérience !

Avis personnel : Bidirectionnalité sauf 

certitude d'un seul sens de navigation 
nécessaire

Inconvénient : plus de complexité pour gérer 

la cohérence :

● Ajouter 2 méthodes : addMaladie, 

removeMaladie

● Toujours passer par ces méthodes

● Supprimer le setter de la collection 


---

94

(setMaladies)

Modifier le getter pour renvoyer une 

12.03.25

Assocation @OneToOne (1 - 1)

Attention : Type d'association non-

normalisée !

On peut a priori fusionner les tables

Toujours à utiliser avec @JoinColumn

Si utilisation en bidirectionnel, difficulté pour 

maîtriser les requêtes (abordé plus loin)


---

95

12.03.25

Assocation @ManyToMany (* - *)

Les 2 extrémités doivent être des Collection

Passe obligatoirement par une table 

d'association

Hibernate utilise des noms de tables et 

colonnes par défaut

Peuvent se surcharger avec : @JoinTable

Possible en bi-directionnel (cf exemple) ou en 

mono-directionnel


---

96

12.03.25

Relation bidirectionnelle : @ManyToMany

Ordonnance

n

n

Soin

Graphe Objet

En BDD

public class Ordonnance {

@ManyToMany
@JoinTable(name="OrdonnanceSoin", 

public class Soin {

@ManyToMany(mappedBy="soins")
private Set ordonnances;

joinColumns=@JoinColumn(name="idOrdonnance"),
inverseJoinColumns =@JoinColumn(name="idSoin"))

}

private Set soins;

}


---

97

12.03.25

Utilisation des Map

Les associations peuvent se matérialiser en 

Java avec des Map

Il faut alors préciser la clef à utiliser avec :

● @MapKeyColumn : en renseignant le 

nom de la colonne de la table liée

● ou

● @MapKey : en renseignant le nom d'un 

attribut de la table liée

● @MapKeyEnumerated : si la clef est un 

@OneToMany(mappedBy="driver") @MapKeyEnumerated(EnumType.STRING
private Map carMap;

enum


---

98

● @MapKeyTemporal : si la clef est une 

12.03.25

date

Opérations en Cascade

Il est possible de propager sur les « enfants » 
les opérations faîtes sur un objet « parent » (ie 
porteur de l’association)

Exemple : Suppression du parent → 

suppression des enfants

Ce comportement se configure avec l'attribut 
Cascade des annotations d'associations. Ex : 
@OneToMany(cascade=CascadeType.ALL)

Choix des opérations à propager :

● CascadeType.ALL : Toutes les 


---

99

opérations

12.03.25

● CascadeType.PERSIST : Opérations 

Opérations en Cascade

Se poser la question : Les enfants ont-ils une 

existence indépendante de leur parent ?

Cas de figure fréquent où les objets-enfants 

non pas vocation à exister indépendamment

Attribut orphanRemoval : permet de signifier 
à Hibernate qu’il doit supprimer les éléments 
« orphelins »

Les éléments supprimés d’une collection 

seront donc supprimés de la base aussi 
(DELETE)


---

100

Le formateur décline toutes responsabilités 

12.03.25

dans l’application de ces principes dans le 

TP 5 : Mapper les associations

Mapper les liens bidirectionnels entre :

● Entreprise → Secteur

● Indice → Secteur

● Entreprise → Declaration

Lancer TestGenerationScriptClass : comparer 

le script obtenu avec celui fourni

On suppose que les déclarations dépendent 

des entreprises, et les indices des secteurs. 
Positionner l’option « cascade » aux endroits 
où elle est pertinente


---

101

Vérifier le fonctionnement avec 

MappingAssociationTest

12.03.25

Utilisation du

LazyLoading


---

102

12.03.25

Récupérer les associations depuis la BDD

Ce qui a été vu :

● Mapper les classes et leurs attributs

● Mapper les relations d’héritage

● Récupérer et manipuler une instance 

d'objets

● Mapper les associations entre les 

classes

Question : Comment récupérer les objets 

avec leurs associations instanciées ?


---

103

Comment parcourir le graphe objet en 

12.03.25

suivant les liens d’association ?

Récupérer les associations depuis la BDD

Plusieurs techniques existantes :

● S'appuyer sur le « LazyLoading »

● Faire des requêtes JPQL/HQL

● Faire des requêtes Criteria

● Toujours possibilité de faire du SQL 

(mais on perd tout l'intérêt du mapping, 
déconseillé)

Nous verrons dans cette partie la technique 

dite du « LazyLoading »


---

104

12.03.25

LazyLoading

Comme son nom l'indique, c'est la technique 

la moins coûteuse

Attention !!! C'est aussi la technique qui est 

souvent source de problèmes de 
performances

Simple parcours du graphe objet en Java

JPA s'occupe de récupérer les objets liés

Génération transparente de requêtes SQL → 

A surveiller ! (show\_sql=true)


---

105

12.03.25

LazyLoading : Exemple

Secteur secteur = entityManager.find(Secteur.class, 1);

for (Entreprise entreprise : secteur.getEntreprises()) {

for (Declaration declaration :

entreprise.getDeclarations().values()) {

logger.info("L'entreprise " +

}

}


---

106

entreprise.getDenomination() +
" a déclaré " + declaration.getMontant() +
" pour le mois de " +
declaration.getDate());

12.03.25

LazyLoading : Exemple

Secteur secteur = entityManager.find(Secteur.class, 1);

for (Entreprise entreprise : secteur.getEntreprises()) {

for (Declaration declaration :

entreprise.getDeclarations().values()) {

logger.info("L'entreprise " +

entreprise.getDenomination() +
" a déclaré " + declaration.getMontant() +
" pour le mois de " +
declaration.getDate());

}

}


---

107

Et ça marche !!!

12.03.25

LazyLoading : Exemple

Secteur secteur = entityManager.find(Secteur.class, 1);

SELECT * FROM SECTEUR
WHERE ID = 1

Requêtes SQL générées

for (Entreprise entreprise : secteur.getEntreprises()) {

for (Declaration declaration :

entreprise.getDeclarations().values()) {

logger.info("L'entreprise " +

}

}


---

108

entreprise.getDenomination() +
" a déclaré " + declaration.getMontant() +
" pour le mois de " +
declaration.getDate());

12.03.25

LazyLoading : Exemple

Requêtes SQL générées

Secteur secteur = entityManager.find(Secteur.class, 1);

for (Entreprise entreprise : secteur.getEntreprises()) {

SELECT * FROM SECTEUR
WHERE ID = 1

SELECT * FROM ENTREPRISE
WHERE FK\_SECTEUR\_ID = 1

for (Declaration declaration :

entreprise.getDeclarations().values()) {

logger.info("L'entreprise " +

}

}


---

109

entreprise.getDenomination() +
" a déclaré " + declaration.getMontant() +
" pour le mois de " +
declaration.getDate());

12.03.25

LazyLoading : Exemple

Requêtes SQL générées

Secteur secteur = entityManager.find(Secteur.class, 1);

for (Entreprise entreprise : secteur.getEntreprises()) {

SELECT * FROM SECTEUR
WHERE ID = 1

SELECT * FROM ENTREPRISE
WHERE FK\_SECTEUR\_ID = 1

for (Declaration declaration :

entreprise.getDeclarations().values()) {

SELECT * FROM DECLARATION
WHERE FK\_ENTREPRISE\_ID = ?

logger.info("L'entreprise " +

entreprise.getDenomination() +
" a déclaré " + declaration.getMontant() +
" pour le mois de " +
declaration.getDate());

}

}


---

110

12.03.25

LazyLoading

On récupère un objet, et on parcours 

librement les associations

Très confortable… mais quelques risques à 

surveiller de près :

● La fameuse LazyInitializationException

● Les risques en termes de performance


---

111

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

112

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

113

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

114

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

115

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

116

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

Database

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

117

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

find, query

Database

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

118

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

find, query

Database

r
e
m
o
v
e

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

119

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

find, query

Database

r
e
m
o
v
e

Removed

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

120

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

find, query

p
e
r
s
s
t

i

r
e
m
o
v
e

Removed

Database

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

121

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

find, query

p
e
r
s
s
t

i

r
e
m
o
v
e

Removed

Database

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

122

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

find, query

p
e
r
s
s
t

i

r
e
m
o
v
e

Removed

Database

Detached

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

123

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

find, query

p
e
r
s
s
t

i

Database

Detached

clear, close

r
e
m
o
v
e

Removed

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

124

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

find, query

p
e
r
s
s
t

i

flush, commit

Database

Detached

clear, close

r
e
m
o
v
e

Removed

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

125

12.03.25

LazyInitialisationException : comprendre le 
cycle de vie des Entités

new

New/Transient

persist

Managed

find, query

Ici pas de
LazyInitializationException !!!

Detached

clear, close

r
e
m
o
v
e

p
e
r
s
s
t

i

flush, commit

Database

Removed

* Les opérations sur les flèches correspondent aux méthodes proposées par l'EntityManager


---

126

12.03.25

LazyInitializationException

Le LazyLoading ne peut avoir lieu qu'avec les 

objets en état : MANAGED (ie « géré »)

Sinon → LazyInitializationException: failed to lazily initialize…

Si la transaction a été fermée, on ne peut plus 

faire de LazyLoading

→ Nécessité d’« encadrer » le traitement au 

sein d’une transaction

On peut réattacher un objet au sein d'une 
autre transaction avec entityManager.merge() 
mais… c’est rare d’avoir réellement besoin de 
plusieurs transactions

12.03.25


---

127

Déterminer le bon niveau pour ouvrir la 

Logique des traitements classiques

Ouverture de la transaction

Récupération des objets

Traitements sur les objets

Optionnel : Détachement des objets qu'on ne 

veut pas modifier en base

Fermeture de la transaction

Flush des opérations vers la BDD 

(automatique)


---

128

12.03.25

Inconvénients du LazyLoading

Très confortable mais… les requêtes sont 

générées au coup-par-coup

Pas d’anticipation sur les données qui sont 

nécessaires → Multiplication du nombre de 
requêtes

Peut conduire à des problèmes de 

performances (problème du SELECT N+1)

Ceux-ci n’apparaîtront souvent pas dans les 

tests unitaires ou sur des jeux en volume 
réduit !


---

129

À utiliser avec parcimonie, lorsque les 
volumes ne constituent pas un problème

12.03.25

TP 6 : Réaliser un traitement avec le LazyLoading

Objectif : Méthode qui calcule les Indices 

mensuels et annuels d’un secteur

Valeur d’un Indice = somme des montants 

déclarés par les entreprises à la date de 
l’indice

Compléter la méthode calculerIndiceSecteur 

de SecteurServicesImpl

Pour les conversion Date → YearMonth et 
Date → Year se référer aux Converter du TP4

Tester avec SecteurServicesTest


---

130

Observer avec le debugger les requêtes 

12.03.25

générées

Stratégie de

chargement complexe


---

131

12.03.25

Lazy attitude

On sait donc maintenant mapper les classes 

et leurs liens (héritage association)

Récupérer/modifier/créer/supprimer les 

entités

Et maintenant parcourir notre graphe d’objets 

avec une méthode très simple

… qui a quelques inconvénients dans 

certains contextes


---

132

12.03.25

Lazy mais pas trop

Le LazyLoading c'est beau mais…

Multiplication du nombre de requêtes

Accès à la base trop nombreux → Source très 

fréquente de mauvaises performances

Mieux vaut 1 « grosse » requête qui charge 

tout, et faire les traitements ensuite

Typiquement le cas pour des batchs sur un 

volume important


---

133

12.03.25

Problème du N + 1 Selects

for (Entreprise entreprise : secteur.getEntreprises()) {

SELECT * FROM ENTREPRISE WHERE

1 requête

for (Declaration declaration :

1 requête x Nb entreprises

entreprise.getDeclarations().values()) {

SELECT * FROM DECLARATION WHERE

...

}

N + 1 requêtes

On pourrait utiliser une jointure

SELECT * FROM ENTREPRISE
JOIN DECLARATION
WHERE …

Gain de performance proportionnel au nombre 

d'entreprises


---

134

12.03.25

Stratégie de chargement

On cherche donc à charger plusieurs objets 

du graphe d'un coup

On veut aussi contrôler les objets qui sont 

chargés

Peut se configurer au niveau du mapping

Peut se réaliser avec des API :

● JPQL : Synthaxe proche du SQL 
permettant d'écrire des requêtes 
complexes


---

135

● Criteria : Réalisation de requêtes via la 

12.03.25

constitution d'objet Java

Stratégie de chargement : Mapping

Pour toutes les associations @****To**** on 

peut définir un attribut fetch :

● fetch=FetchType.EAGER : L'objet (ou la Collection) lié 
sera systématiquement chargé avec l'objet portant 
l'association

● fetch=FetchType.LAZY : L'objet (ou la Collection) lié ne 
sera pas chargé avec l'objet portant l'association, 
mais à la demande (LazyLoading, JPQL…)

Par défaut, toutes les associations 

@***ToOne ont un FetchType = EAGER (la 
faute à la norme JPA)


---

136

Par défaut, toutes les associations 
@***ToMany ont un FetchType = LAZY

12.03.25

Stratégie de chargement : Mapping

Conseil : Mettez toutes vos associations en 

FetchType = LAZY !!!

Cause très fréquente de dégradations des 

performances

Mieux vaut contrôler le chargement des 

associations

En cas de nécessité absolue de 
FetchType.EAGER, possibilité de 
configuration avec @Fetch


---

137

12.03.25

Java Persistence

Query Langage

(JPQL)


---

138

12.03.25

Stratégie de chargement : JPQL

La configuration au niveau du mapping offre 

peu de possibilité

Besoin d’une méthode plus souple, et 

permettant de composer des requêtes plus 
poussées

2 possibilités : JPQL ou Criteria

JPQL : Il s'agit d'une syntaxe proche du SQL

La syntaxe fait référence aux entités Java 

mappées avec JPA, au lieu des tables et 
colonnes du SQL

12.03.25


---

139

Permets de réaliser des requêtes de 

Stratégie de chargement : JPQL

On peut réaliser quasiment tous les types de 

requêtes correspondant au SQL

Certaines restrictions peuvent concerner les 
UPDATEs, les INSERTs massifs, les DELETEs

De nombreuses fonction du SQL sont 

disponibles (consulter la doc)

Attention : Faire des requêtes simples autant 

que possible (conseil perso)

Conseil : Select avec jointure explicite (JOIN, 

LEFT JOIN), éviter les sous-requêtes…

12.03.25


---

140

Conseil d'utilisation du JPQL

Tant que les volumes restent relativement 

faibles (performances acceptables)

SELECT : Utiliser le JPQL pour récupérer les 

données (peu/pas de LazyLoading)

UPDATE : Faire les modifications sur les 

objets Java via les setters

INSERT, DELETE :Insérer ou supprimer via 

l'EntityManager (entityManager.persist(), 
entityManager.remove())


---

141

12.03.25

JPQL : Synthaxe SELECT

Toutes les Table/Entité doivent avoir un alias

● Ex : SELECT med FROM Medecin med

● (on omet le AS du SQL)

On parcourt le graphe objet avec le point (.) 

en faisant référence aux attributs dans la 
classe

● Ex : SELECT med FROM Medecin med

WHERE med.nom IS NOT NULL

Cette syntaxe est proche de l'OGNL de struts


---

142

12.03.25

JPQL : Les paramètres

Attention : Ne pas concaténer les paramètres 

dans la requête (valable hors JPA)

→ Risque d'injection SQL

Il est conseillé de passer des paramètres aux 

requêtes avec un alias

● Ex : SELECT med FROM Medecin med

●

WHERE med.nom = :paramNom

On renseigne les paramètres ensuite dans 

l'objet Query (équivalent JPA du 
PrepareStatement)


---

143

12.03.25

JPQL : Création des requêtes

La création de la requête se fait via 

l'EntityManager :

entityManager.createQuery(requeteHql, TypeRetour.class)

2 objets possibles :

● Query : Sans type retour défini

● TypedQuery : avec type retour (conseillé)

On renseigne les paramètres :

● query.setParameter(''paramNom'', nom)

Utile : le paramètre peut être un objet ou un 

tableau (pour les clauses IN)

12.03.25


---

144

JPQL : Récupération des résultats

Une fois la requête constituée et les 

paramètres éventuels renseignés

On peut récupérer les résultats de plusieurs 

manières :

● query.getResultList() (renvoie une liste d’objets)

● query.getSingleResult() (attends un résultat unique 

sinon → NonUniqueResultException )

● query.getResultStream() (résultat sous forme de 

Stream)

getResultList() et getSingleResult() récupère la 

totalité du ResulSet en une opération


---

145

Peut être problématique en cas de volume de 

12.03.25

données important, risque de surcharge du 

JPQL : Les jointures

On peut faire tous les types de jointures :

● Interne : SELECT med FROM Medecin 

med

●

JOIN med.patients patient

● Externe : SELECT med FROM Medecin 

med

●

patient

LEFT JOIN med.patients 

● Cartésienne (dites Cross-Join)(A éviter !!!) :


---

146

●

●

SELECT med FROM Medecin med, 

Patient patient

12.03.25

WHERE patient.medecin = med

JPQL : Les jointures

SELECT * FROM medecin
INNER JOIN patient ON patient.id\_medecin = medecin.id

On peut faire tous les types de jointures :

● Interne : SELECT med FROM Medecin 

med

●

JOIN med.patients patient

● Externe : SELECT med FROM Medecin 

med

●

patient

LEFT JOIN med.patients 

● Cartésienne (dites Cross-Join) :


---

147

●

●

SELECT med FROM Medecin med, 

Patient patient

12.03.25

WHERE patient.medecin = med

JPQL : Les jointures

On peut faire tous les types de jointures :

● Interne : SELECT med FROM Medecin 

SELECT * FROM medecin
INNER JOIN patient ON patient.id\_medecin = medecin.id

med

●

SELECT * FROM medecin
LEFT JOIN patient ON patient.id\_medecin = medecin.id

JOIN med.patients patient

● Externe : SELECT med FROM Medecin 

med

●

patient

LEFT JOIN med.patients 

● Cartésienne (dites Cross-Join) :


---

148

●

●

SELECT med FROM Medecin med, 

Patient patient

12.03.25

WHERE patient.medecin = med

JPQL : Les jointures

On peut faire tous les types de jointures :

● Interne : SELECT med FROM Medecin 

SELECT * FROM medecin
INNER JOIN patient ON patient.id\_medecin = medecin.id

med

●

SELECT * FROM medecin
LEFT JOIN patient ON patient.id\_medecin = medecin.id

JOIN med.patients patient

SELECT * FROM medecin, patient
WHERE patient.id\_medecin = medecin.id

● Externe : SELECT med FROM Medecin 

med

●

patient

LEFT JOIN med.patients 

● Cartésienne (dites Cross-Join) :


---

149

●

●

SELECT med FROM Medecin med, 

Patient patient

12.03.25

WHERE patient.medecin = med

JPQL : Chargement des associations

On voit donc le besoin des liens objets pour 

pouvoir parcourir le graphe

→ cf. Besoin d’un modèle objet « propre »

On peut récupérer les objets avec leurs 

associations instanciées

On peut récupèrer ainsi des « portions » du 

graphe objet :

→ On élimine le problème du « Select N+1 »

Possible avec les jointures internes ou 

externes


---

150

Utilisation du mot clef : FETCH

12.03.25

JPQL : Chargement des associations

Par exemple, si on veut récupérer le Medecin 

avec ses Patient(s) instanciés :

SELECT med FROM Medecin med

JOIN FETCH med.patients patient

JPA retournera donc un objet Medecin, dont 

l’attribut « patients » contient une collection 
d’objets Patient

On peut aussi faire une jointure externe :

● LEFT JOIN FETCH med.patients…


---

151

● (On aura alors aussi les médecins qui 

12.03.25

n'ont pas de patients)

JPQL : Chargement des associations

Comme on a vu, on peut ajouter des critères 

sur les objets de l'association :
SELECT med FROM Medecin med

JOIN FETCH med.patients patient

WHERE patient.nom = :paramNomPatient

On récupère alors un Medecin mais les 

patients instanciés sont seulement ceux qui 
vérifient les critères du WHERE

Ici on aura les Medecin avec dans leurs 

collection de patients uniquement les patients 
dont le nom vaut paramNomPatient

12.03.25


---

152

L'objet est donc « incomplet » → Peut 

JPQL : Chargement des associations

Les requêtes sont analysées à l'exécution → 
Potentielles erreur sur le nom des classes ou 
attributs

On peut utiliser @NamedEntityGraph ou 
@NamedQuery pour stocker au niveau des 
entités les requêtes

Elles sont alors analysées au démarrage du 

serveur

Moins souple que JPQL puisque défini au 

sein du mapping


---

153

12.03.25

JPQL : Chargement des associations

On définit donc avec JPQL des portions de 

graphe qu'on va charger en 1 fois

Il vaut mieux charger tout ce dont on a besoin 

en 1 fois… mais que ce dont on a besoin !

Cela peut conduire à des requêtes longues 

(nb caractères), mais le traitement est en 
général plus court

N'hésitez pas à tester pour s'en assurer !

Attention : Ne pas oublier les indexs sur les 

clefs étrangères pour Postgres !!!


---

154

12.03.25

JPQL : Chargement des associations : Ex

Ex : Traitement concernant le dossier médical 

des patients d'un médecin


---

155

12.03.25

JPQL : Chargement des associations : Ex

Ex : Traitement concernant le dossier médical 

des patients d'un médecin


---

156

12.03.25

JPQL : Chargement des associations : Ex

Ex : Traitement concernant le dossier médical 

des patients d'un médecin


---

157

12.03.25

JPQL : Chargement des associations : Ex

Ex : Traitement concernant le dossier médical 

des patients d'un médecin


---

158

12.03.25

JPQL : Chargement des associations : Ex

Ex : Traitement concernant le dossier médical 

des patients d'un médecin


---

159

12.03.25

JPQL : Chargement des associations : Ex

Ex : Traitement concernant le dossier médical 

des patients d'un médecin

SELECT med FROM Medecin med

JOIN FETCH med.consultations consult

JOIN FETCH consult.patient patient

JOIN FETCH patient.dossierMedical 

dossier

WHERE med.id = :paramId

On récupère le médecin avec tous les objets 

liés nécessaires


---

160

12.03.25

On ne doit avoir ensuite aucune requête 

JPQL : De nombreuses autres possibilités

Possibilités de faire des « projections » :

SELECT med.nom, med.age FROM 

Medecin med

getResultList() renvoie alors une List de 

tableau d'objet (List)

● Dans l'exemple : object[0] → nom, 

object[1] → age

Conseil : Travailler autant que possible avec 

des objets complets

Utile en cas de problème de performance dû 

12.03.25


---

161

à de trop nombreuses colonnes

JPQL : De nombreuses autres possibilités

Fonctions d'agrégation disponibles :

● COUNT, AVG, MIN, MAX, SUM

Possibilité de faire des GROUP BY et ORDER 

BY

Fonction chaîne de caractère :

● CONCAT, SUBSTRING, UPPER, 

LENGTH…

Fonction mathématique :

● ABS, MOD, SQRT …


---

162

Fonction date :

12.03.25

● CURRENT\_DATE/TIME/TIMESTAMP

TP 7 : Requête de chargement complexe

Ecrire le contenu de la méthode 

findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL dans 
SecteurDaoImpl permettant de récupérer un 
secteur avec ses entreprises et leurs 
déclarations

Lancer le test SecteurServicesPerformancesTestJPQL

Observer les requêtes générées avec 

show\_sql=true

Examiner le code du test et la méthode pour 

vérifier qu’il n’y a qu’une requête générée


---

163

12.03.25

L’API Criteria


---

164

12.03.25

L'API Criteria

JPA propose une autre manière de réaliser 

des requêtes complexes

Criteria est une API qui permet de construire 

des requêtes à partir d'objets Java

Criteria permet de rendre les requêtes « Type-

Safe », ie fortement typée

Criteria permet de créer des requêtes 

dynamiques (ajout de clause selon le contexte 
par ex)

Mais requêtes moins lisibles, construction un 


---

165

peu plus complexe

12.03.25

Plusieurs utilisations sont possibles…

L'API Criteria : Des requêtes fortement typées

On a vu avec JPQL, que les requêtes étaient 

des chaînes de caractère

Mais que se passe-t-il si un attribut, ou une 

classe change de nom ?…

Il faut modifier toutes les requêtes qui y font 

référence !

Pas d'erreurs de compilation → Couverture 
par les tests obligatoire ou risque de plantage 
en prod !!!


---

166

L'API Criteria permet d'avoir un lien direct, 
organique, entre les requêtes et les objets sur 
lesquels elles portent

12.03.25

L'API Criteria : Des requêtes fortement typées

Exemple :

CriteriaBuilder builder = entityManager.getCriteriaBuilder();

On crée l'objet qui va nous
permettre de construire la
requête


---

167

12.03.25

L'API Criteria : Des requêtes fortement typées

Exemple :

CriteriaBuilder builder = entityManager.getCriteriaBuilder();

CriteriaQuery criteria = builder.createQuery( Secteur.

On crée la requête
en indiquant le type retour


---

168

12.03.25

L'API Criteria : Des requêtes fortement typées

Exemple :

CriteriaBuilder builder = entityManager.getCriteriaBuilder();

CriteriaQuery criteria = 

builder.createQuery( Secteur.

Root root = criteria.from( Secteur.class );

On indique l'entité de laquelle
on part (correspond à ce qui
suit FROM)


---

169

12.03.25

L'API Criteria : Des requêtes fortement typées

Exemple :

CriteriaBuilder builder = entityManager.getCriteriaBuilder();

CriteriaQuery criteria = 

builder.createQuery( Secteur.

Root root = criteria.from( Secteur.class );

criteria.select( root );

On indique le type de
requête (SELECT, UPDATE…)


---

170

12.03.25

L'API Criteria : Des requêtes fortement typées

Exemple :

CriteriaBuilder builder = entityManager.getCriteriaBuilder();

CriteriaQuery criteria = 

builder.createQuery( Secteur.

Root root = criteria.from( Secteur.class );

criteria.select( root );

criteria.where( builder.equal( root.get( "codeNaf" ), codeNaf ) );

On ajoute des critères (correspond
à la clause WHERE)


---

171

12.03.25

L'API Criteria : Des requêtes fortement typées

Exemple :

CriteriaBuilder builder = entityManager.getCriteriaBuilder();

CriteriaQuery criteria = 

builder.createQuery( Secteur.

Root root = criteria.from( Secteur.class );

criteria.select( root );

criteria.where( builder.equal( root.get( "codeNaf" ), codeNaf ) );

List secteurs = entityManager.createQuery( criteria


---

172

12.03.25

On récupère le résultat

L'API Criteria : Des requêtes fortement typées

Exemple :

CriteriaBuilder builder = entityManager.getCriteriaBuilder();

CriteriaQuery criteria = 

builder.createQuery( Secteur.

Root root = criteria.from( Secteur.class );

criteria.select( root );

criteria.where( builder.equal( root.get( "codeNaf" ), codeNaf ) );

List secteurs = entityManager.createQuery( criteria

Equivalent JPQL :

SELECT secteur FROM Secteur

WHERE codeNaf = :paramCodeNaf


---

173

12.03.25

L'API Criteria : Des requêtes fortement typées

Ainsi si la classe Secteur devient SecteurNaf, 

la requête reste valide

On voit qu’on pourrait ajouter ou enlever des 

conditions au WHERE programmatiquement

Utile pour les tableaux avec filtres 

dynamiques

En revanche il reste les attributs (dans les 

critères), qui restent non-typés :

criteria.where( builder.equal( root.get( "codeNaf" ), codeNaf ) );

Il est possible d'utiliser le « JPA Metamodel 


---

174

Generator »

12.03.25

Permets de générer des classes qui sont 

L'API Criteria : JPA Metamodel Generator

Classes générées par JPA Metamodel 

Generator :

@Generated(value = 
"org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")

@StaticMetamodel(Secteur.class)

public abstract class Secteur\_ {

public static volatile SetAttribute indices;

public static volatile SingularAttribute 

libelleNomenclature;

public static volatile SingularAttribute 

codeNaf;

public static volatile SetAttribute 

entreprises;


---

175

12.03.25
public static volatile SingularAttribute id;

}

L'API Criteria : Des requêtes fortement typées

Les attributs des classes générées sont 

statiques

On peut donc dans les requêtes faire 
référence aux attributs en tant qu'objet :

criteria.where( builder.equal( root.get( Secteur\_.codeNaf ), codeNaf ) );

On arrive donc à des requêtes entièrement 

typées

Pour générer les classes du métamodèle, un 
peu de config Maven + plugin Eclipse « m2e »

Les classes générées se mettent à jour 


---

176

automatiquement lorsque les entités évoluent

12.03.25

L'API Criteria : Conclusion

L'API Criteria permet d'avoir des requêtes 

totalement typées

Assez utile pour générer des requêtes 
dynamiques qui s’adaptent au contexte 
(tableau avec filtres optionnels)

Difficulté réside dans son appropriation, et 

son manque de lisibilité

Conseil : Si utilisation de Criteria, alors 

utilisation complète avec Métamodel


---

177

12.03.25

TP 8 : Requête de chargement complexe avec Criteria

Réaliser une méthode de récupération d'un 

secteur avec ses entreprises et leur 
déclaration avec l'API Criteria

Lancer le test qui vérifie qu'il n'y a qu'une 
requête (SecteurServicesPerformancesTestCriteria)

Observer les requêtes générées avec 

show\_sql=true


---

178

12.03.25

Configuration

d’Hibernate


---

179

12.03.25

La configuration de JPA/Hibernate

La configuration s'est simplifiée avec les 

nouvelles versions

Un seul fichier optionnel : persistence.xml

Normalement dans le répertoire META-INF

Normalement, on y déclare les infos pour la 

connexion (url, username, password… )

Sauf que, comme on utilise InseeConfig, ou 

Spring, pour les properties, on y déclare 
souvent le strict minimum… ou rien du tout


---

180

12.03.25

La configuration de JPA/Hibernate

Exemple :




Persistence unit pour formation Hibernate 5




Seule info : le nom de l'unité de persistence

Unité de persistence : Objet correspondant 

conceptuellement à une source de données

Plusieurs unités de persistence possibles si 

plusieurs BDD par ex. (rare normalement)


---

181

12.03.25

La configuration de JPA/Hibernate

JPA utilise essentiellement 2 composants :

● EntityManagerFactory : 1 

EntityManagerFactory est créé par unité 
de persistence, au démarrage du serveur 
(ou du batch)

● EntityManager : on l'a déjà utilisé, est 
créé par le composant précédent, en 
général 1 par transaction

L'EntityManagerFactory contient les 
métadonnées sur le mapping des objets


---

182

EntityManagerFactory, objet de plus haut 

12.03.25

niveau, plus long à créer

La configuration de JPA/Hibernate

Lorsqu'on utilise Spring-Boot ou Spring, on 
préfère passer les infos de connexions via les 
properties Spring

On crée donc souvent l'EntityManagerFactory 

dans les fichiers Spring

Avec Spring-Boot la création de 

l'EntityManagerFactory est transparente


---

183

12.03.25

Configuration avec Spring-Boot

Spring-Boot permet de disposer d’une 

configuration JPA/Hibernate avec très peu de 
code

Il suffit d’ajouter le starter « data-jpa » au 

pom

On renseigne ensuite la chaîne 

spring.datasource.url

Spring-Boot en déduit alors le type de base 
données et met en place tous les composants 
nécessaires


---

184

Spring-Boot met en place un pool de 

12.03.25

connexion « Hikari » plus performant que 

Récupération des composants JPA

On peut ensuite injecter l'EntityManager dans 

une classe avec @PersistenceContext ou 
@Autowire (Spring) :

@PersistenceContext

private EntityManager entityManager;

Attention : Par défaut celui-ci n'existe que 

dans lorsqu'une transaction est ouverte !

De même, la récupération de 

l'EntityManagerFactory se fait avec 
@PersistenceUnit ou @Autowire


---

185

12.03.25

Le pattern GenericDao

Ce pattern est utilisé pour factoriser les 

méthodes de base de l'EntityManager

On définit une interface GenericDao que tous 

les DAOs implémentent

Une classe abstraite AbstractGenericDao qui 

récupère l'EntityManager et qui lui délègue :

● persit()

● merge()

● remove()

● find()

● …


---

186

12.03.25

Logger les requêtes : un outil de survie !

On l'a vu, JPA peut conduire à multiplier les 

requêtes et dégrader les performances

Il faut donc surveiller les requêtes générées 

lors des Tests manuels ou automatisés

Plusieurs outils à dispo :

● hibernate.show\_sql = true (pour logger 

les requêtes)

● hibernate.format\_sql = true (pour 

Hibernate: 
select secteur0\_.id as id1\_5\_0\_, secteur0\_.codeNaf as codeNaf2\_5\_0\_, secteur0\_.libelleNomenclature as libelleN3\_5\_0\_

formatter le sql)

from

test.Secteur secteur0\_

where

● Hibernate.use\_sql\_comments = true 

secteur0\_.id=?

(avoir des commentaires)

12.03.25


---

187

Permets de tracer les requêtes générées. Ex :

Utiliser un wrapper de DataSource

La configuration via la DataSource permet de 

mettre en place simplement un wrapper

Permet d’ajouter des fonctionnalités : ex. 

logger les requêtes avec les paramètres

Peut-être utile pour exécuter simplement les 
requêtes sur la base et s'assurer de l'origine 
des problèmes de perfs (nb de requêtes, ou 
requêtes trop longues)

D'autres fonctionnalités intéressantes…


---

188

12.03.25

Utiliser un wrapper de DataSource

Il existe plusieurs wrapper de datasource 

dispo. Ex :

● P6spy

● Datasource-proxy

● Log4jdbc…

P6spy permet d'avoir la requête avec les 

paramètres renseignés

Datasource-Proxy propose une fonctionnalité 

supplémentaire très intéressante : Un 
compteur des requêtes envoyées !

12.03.25

Ainsi on peut faire un comptage dans le test 

unitaire pour s'assurer de détecter toute 


---

189

Génération des scripts SQL

Hibernate permet de générer les scripts de 
base de données, ainsi que les màj auto du 
schéma

En phase de développement, très utile

En phase de production, peut-être une aide, 

mais doit être testée (pas de màj directe)

Un outil d'historisation des modifs du 

schéma est aussi souhaitable en prod (Flyway 
ou Liquibase)

Paramètre à renseigner :


---

190

● javax.persistence.schema-

generation.database.action : Type 

12.03.25

OpenEntityManagerInView

On a vu qu'on ne pouvait pas utiliser le 
LazyLoading en dehors d'une transaction

Mais parfois, on voudrait l'utiliser, 

simplement pour de l'affichage par ex., et sans 
faire de modification

On doit alors ouvrir une transaction pour 

simplement lire des données… Pas très 
cohérent !

Dans le contexte d'une appli web, ou d'un 
web-service, on peut ouvrir un EntityManager 
en mode lecture seulement tant qu'aucune 
transaction n'est déclarée

12.03.25


---

191

OpenEntityManagerInView

Il faut ajouter un filtre au web.xml, Spring en 



propose un :

openEntityManagerInViewFilter
org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter

entityManagerFactoryBeanName
myEmf (ici le nom du bean pour l'EntityManagerFactory)




openEntityManagerInViewFilter
/*



---

192

12.03.25

Spring Data : Une surcouche fort sympathique !

Module de la galaxie Spring

Se configure simplement en passant 
l’entityManagerFactory à un bean Spring

Ou avec Spring-Boot de manière transparente

Permet de réduire encore la partie technique :

● entityManager.createQuery

● entityManager.getResultList

● entityManager.setParameter


---

193

● ...

12.03.25

Spring Data : Une surcouche fort sympathique !

En mode Spring (sans Spring-Boot)

Une dépendance à ajouter :



org.springframework.data
spring-data-jpa



Un bean à configurer :




---

194

12.03.25

Spring Data : Une surcouche fort sympathique !

Et c’est la fête !

public interface SecteurRepository extends Repository {

public Secteur findByCodeNaf(String codeNaf);

}

L’implémentation est générée par Spring-Data 

JPA

On dispose de méthodes CRUD basiques


---

195

Les méthodes déclarées supplémentaires 

12.03.25

sont implémentées par convention de 

Spring Data : Une surcouche fort sympathique !

Pour des méthodes plus complexes on 

utilisera @Query dans l’interface

public interface SecteurRepository extends Repository {

Ex :

public Secteur findByCodeNaf(String codeNaf);

@Query("SELECT sect FROM Secteur sect "

+ "JOIN FETCH secteur.entreprises entreprise "
+ "JOIN FETCH entreprise.declarations declaration "
+ "JOIN FETCH sect.indices indice "
+ "WHERE sect.codeNaf = :codeNaf")

public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL(String codeNaf);

}


---

196

12.03.25

JPA, Hibernate de nombreuses autres fonctionnalités

Mise en place de verrous sur les données

Mise en place d'un cache de données (pour 

les données de référence)

Historisation des données

Tunning pour l'écriture et la lecture des 

données

Utilisation des curseurs de BDD (pour les très 

gros volumes en batch)

Gestion des données spatiales (par ex. avec 

PostGis)


---

197

12.03.25

Et bien d'autres… Mais ce sera pour une 

Merci de votre attention

Avez-vous des questions ?

www.insee.fr

@InseeFr

