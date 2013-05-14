5 cards comparaison test

Scenario: comparaison kicker vs paire
Given la main 1 dispose des cartes suivantes (KICKER) : A-S, 2-C, 3-S, K-S, J-D
And la main 2 dispose des cartes suivantes (PAIR) : 10-S, 2-S, 5-C, 5-D, 8-S
Then la meilleure main est la main 2

Scenario: comparaison suite vs couleur vs brelan
Given la main 1 dispose des cartes suivantes (STRAIGHT) : 4-S, 2-C, A-S, 3-S, 5-D
And la main 2 dispose des cartes suivantes (FLUSH) : 10-S, 2-S, 5-S, K-S, 8-S
And la main 3 dispose des cartes suivantes (THREE_OF_A_KIND) : 5-S, 2-S, 5-C, 5-D, 8-S
Then la meilleure main est la main 2

Scenario: comparaison kicker moyen vs kicker fort vs kicker faible
Given la main 1 dispose des cartes suivantes (KICKER) : 5-S, 2-C, 6-S, 3-S, 10-D
And la main 2 dispose des cartes suivantes (KICKER) : 10-S, 2-S, A-S, 5-S, 8-C
And la main 3 dispose des cartes suivantes (KICKER) : 5-S, 2-S, 3-C, 4-D, 7-S
Then la meilleure main est la main 2

Scenario: comparaison suite vs suite (cas nominal)
Given la main 1 dispose des cartes suivantes (STRAIGHT) : 6-S, 7-C, 8-C, 9-S, 10-C
And la main 2 dispose des cartes suivantes (STRAIGHT) : 5-S, 6-C, 7-S, 8-S, 9-D
Then la meilleure main est la main 1

Scenario: comparaison suite a l'as faible vs suite a l'as forte
Given la main 1 dispose des cartes suivantes (STRAIGHT) : A-S, 2-C, 3-C, 5-S, 4-C
And la main 2 dispose des cartes suivantes (STRAIGHT) : A-C, K-C, 10-S, Q-S, J-D
Then la meilleure main est la main 2