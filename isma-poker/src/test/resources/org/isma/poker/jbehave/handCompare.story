Five cards comparaison test

Scenario: comparaison kicker vs paire
Given la main 1 dispose des cartes suivantes : ACE-SPADES, TWO-CLUBS, THREE-SPADES, KING-SPADES, KNAVE-DIAMONDS
And la main 2 dispose des cartes suivantes : TEN-SPADES, TWO-SPADES, FIVE-CLUBS, FIVE-DIAMONDS, EIGHT-SPADES
Then la combinaison de la main 1 est : KICKER
Then la combinaison de la main 2 est : PAIR
Then la meilleure main est la main 2


Scenario: comparaison suite vs couleur vs brelan
Given la main 1 dispose des cartes suivantes : FOUR-SPADES, TWO-CLUBS, ACE-SPADES, THREE-SPADES, FIVE-DIAMONDS
And la main 2 dispose des cartes suivantes : TEN-SPADES, TWO-SPADES, FIVE-SPADES, FIVE-SPADES, EIGHT-SPADES
And la main 3 dispose des cartes suivantes : FIVE-SPADES, TWO-SPADES, FIVE-CLUBS, FIVE-DIAMONDS, EIGHT-SPADES
Then la combinaison de la main 1 est : STRAIGHT
Then la combinaison de la main 2 est : FLUSH
Then la combinaison de la main 3 est : THREE_OF_A_KIND
Then la meilleure main est la main 2

Scenario: comparaison kicker moyen vs kicker fort vs kicker faible
Given la main 1 dispose des cartes suivantes : FOUR-SPADES, TWO-CLUBS, ACE-SPADES, THREE-SPADES, TEN-DIAMONDS
And la main 2 dispose des cartes suivantes : TEN-SPADES, TWO-SPADES, ACE-SPADES, FIVE-SPADES, EIGHT-CLUBS
And la main 3 dispose des cartes suivantes : FIVE-SPADES, TWO-SPADES, THREE-CLUBS, FOUR-DIAMONDS, SEVEN-SPADES
Then la combinaison de la main 1 est : KICKER
Then la combinaison de la main 2 est : KICKER
Then la combinaison de la main 3 est : KICKER
Then la meilleure main est la main 2