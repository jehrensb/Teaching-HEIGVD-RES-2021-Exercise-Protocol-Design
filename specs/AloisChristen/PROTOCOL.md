Protocole de communication : TCP

Encodage : UTF-8

Adresse de connection : localhost

Port de connection : 2021

Après la connection, le client est le premier à parler. 

Il envoi un message contenant 
>1) Le nom de l'opération à faire
>2) Le(s) opérande(s)
Chacune des informations est séparée d'un espace. Le bon nombre d'opérande doit être envoyé suivant l'opération demandée


Le serveur répond à la requête :
> Si la requête était correcte, il return "RESPONSE" suivit de la réponse
> 
> Si la requête était incorrecte, il return "ERROR" suivit d'un code d'erreur :
> > "BAD OP" Si l'opération n'est pas supportée
> > 
> > "BAD OPERAND NUMBER" Si le nombre d'opérande n'est pas adéquat (trop ou pas assez)
> > 
> > "BAD REQUEST" Pour tout autre problème


Les nombres doivent être au format double. La réponse est au format double.
Les opérations supportées sont "ADD", "MULT" et "SUB".


Après la requête du client, le serveur clôt la connection.
