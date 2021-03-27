# What transport protocol do we use?
TCP
  
# How does the client find the server (addresses and ports)?
On le lui donne l'adresse statique à l'avance
   
# Who speaks first?
Le serveur peut être déjà allumé mais c'est le client qui essayer de communiquer en premier
 
# What is the sequence of messages exchanged by the client and the server?
-client : Tentative de connexion au port localhost 8080
-serveur : Serveur en écoute reçois le client et lui donne ses possibilités
-client : Envoie le calcul souhaité
-serveur: Reçoit la requéte et fait le calcul puis retourne le résultat au client
-client : Envoie une requète de fin.
-serveur : termine la connexion.
    
# What happens when a message is received from the other party?
Le message va être traité pour lui être renvoyé
  
# What is the syntax of the messages? How we generate and parse them?
Le client envoie un string qu'on va parser pour en déduire les opérandes et ainsi lui renvoyé
la réponse aussi sous forme de string
    
# Who closes the connection and when?
Le client, une fois qu'il entre la commande exit

