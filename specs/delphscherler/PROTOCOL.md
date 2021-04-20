- What transport protocol do we use?

  > TCP/IP

- How does the client find the server (addresses and ports)?

  > Le serveur indique au départ son port d'écoute. Le client peut alors se connecter au serveur à l'aide de l'adresse IP du serveur et de ce port.

- Who speaks first?

  > Le client fait la demande de connexion.

- What is the sequence of messages exchanged by the client and the server? (flow)

  > 1. Le serveur lance l'écoute sur le port désigné
  > 2. Le client tente de se connecter au serveur
  > 3. Le serveur accepte la connexion
  > 4. Le client envoie le calcul à effectuer
  > 5. Le serveur effectue le calcul et envoie la réponse au client
  > 6. Le client peut effectuer un nouveau calcul ou fermer la connexion

- What happens when a message is received from the other party? (semantics)

  > Du côté serveur, il doit interpréter le message et effectuer le calcul, si le calcul n'est pas possible il renvoie une erreur.
  >
  > Du côté client, il affiche le résultat du calcul ou l'erreur reçu par le serveur.

- What is the syntax of the messages? How we generate and parse them? (syntax)

  > ADD nombre1 nombre2
  >
  > MUL nombre1 nombre2
  >
  > RESULT nombre
  >
  > ERROR messageErreur

- Who closes the connection and when?

  > Le client quand il n'a plus besoin de calculer.