A duel to the death between two brothers

Scenario: duel to the death between 2 players with a small blind player sitout
Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre
Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton

When luigi effectue l'action : SIT_OUT()
Then mario a 100 jetons
Then la partie est en standby

Scenario: duel to the death between 2 players with a big blind player sitout
Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre
Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton

When luigi paie la petite blinde
When mario effectue l'action : SIT_OUT()
Then luigi a 100 jetons
Then la partie est en standby

Scenario: duel to the death between 2 players with a sitout on bet1 by smallblind player
Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre
Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton

When luigi paie la petite blinde
When mario paie la grosse blinde
When luigi effectue l'action : SIT_OUT()

Then mario a 105 jetons
Then la partie est en standby

Scenario: duel to the death between 2 players with a sitout on bet1 by big player
Given la table de jeu est vide, la partie commencera lorsque 2 joueurs sont presents
Given les blindes sont à 5/10 jetons
Given le joueur mario dispose de 100 jetons et rentre dans la partie
Given le joueur luigi dispose de 100 jetons et rentre dans la partie

When la partie démarre
Then etape en cours : BLINDS (pot : 0)
Then mario est au bouton

When luigi paie la petite blinde
When mario paie la grosse blinde
When luigi effectue l'action : CALL()
When mario effectue l'action : SIT_OUT()

Then luigi a 110 jetons
Then la partie est en standby