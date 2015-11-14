import iut.algo.*;

/** Streams
  * Jeux du streams pour le projet #2
  * @author Alexis GODIN Thibaut EMION Bilaly CAMARA Thomas DARGENT Maxence POISSON
  * @version 1 du 13/11/2015
  */

public class Streams
{
    /*-- Constante globale--*/
    private static final int JOCKER = -1;

    /*-- Variable globale--*/
    private static boolean debug = true;

    public static void main(String[] args)
    {      
        /*-- Déclaration des variables--*/
        int[]   grille;
        int difficulte;

        /*-- Traitement --*/
        Console.println("Dans quel mode voulez-vous jouer ? \n  1. Normal       2. Expert");
        difficulte = Console.lireInt();

        while ((difficulte != 1) && (difficulte != 2))
        {
            Console.print("Veuillez rentrer une valeur valide (1 ou 2) : ");
            difficulte = Console.lireInt();
        }

        if (debug)
            grille = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, Streams.JOCKER, 2, 11, 12, 13, 14, 15, 16, 1, 18, 19, 20 };
        else
            grille = Streams.initaliserGrilleJoueur();

        Console.effacerEcran();
        Console.println(Streams.grilleEnChaine(grille));

        Console.println("Votre score : " + Streams.compterPoints(grille, difficulte));
    }

    private static int[] initaliserGrilleJoueur()
    {
        /*-- Déclaration des constantes --*/
        final int TAILLE_GRILLE = 20;
        final int NB_TUILE_MAX = 40;

        /*-- Déclaration des variables --*/
        int[]   tabTuile;
        int[]   grille;
        int     tuile, tour, nbJeton, indice;

        /*-- Traitement --*/
        tabTuile = new int[NB_TUILE_MAX];
        grille   = new int[TAILLE_GRILLE];


        // On rempli le tableau avec les tuiles
        nbJeton = 0;
        for (indice = 1; indice <= 30; indice++)
        {
            tabTuile[nbJeton] = indice;

            nbJeton++;

            if (indice >= 11 && indice <= 19)
            {
                tabTuile[nbJeton] = indice;
                nbJeton++;
            }
        }

        tabTuile[NB_TUILE_MAX-1] = -1;

        Console.println(Streams.grilleEnChaine(tabTuile));

        nbJeton = tabTuile.length - 1;

        for (tour = 0; tour < 20; tour++)
        {
            // Affichage de la grille du joueur
            Console.effacerEcran();
            Console.println(Streams.grilleEnChaine(grille));

            // On tire une tuile au hasard dans le tableau des tuiles
            indice = (int)(Math.random() * (nbJeton+1));
            tuile = tabTuile[indice];

            // On place la dernière tuile du tableau dans la case de la tuile tirée
            tabTuile[indice] = tabTuile[nbJeton];
            nbJeton--;
            
            // On demande au joueur de positionner sa tuile
            grille = Streams.positionnerTuile(grille, tuile);
        }

        return grille;
    }
  
    private static int[] positionnerTuile(int[] grille, int tuile)
    {

        /*-- Déclaration des variables --*/
        int position;
        
        /*-- Traitement --*/
        if (tuile == Streams.JOCKER)
            Console.print("Joker ! Placez le dans votre tableau : ");
        else        
            Console.print("Placez le nombre " + tuile + " dans votre tableau : ");
        
        position = (Console.lireInt() - 1); // Le joueur saisi un nombre compris entre 1 et 20
        
        // Tant que la saisi est plus grande/petite que le tableau ou que la case est déjà prise
        while (position < 0 || position > 19 || grille[position] != 0)
        {
            Console.print("Saisie invalide. Veuillez ressaisir : ");
            position = (Clavier.lire_int() - 1);
        }

        grille[position] = tuile;
        
        return grille;
    }

    private static int compterPoints(int[] grille, int difficulte)
    {

        /*-- Déclaration des variables --*/
        int[] tabScore;
        int i;
        int nbTuilleCroiss, score;
        int scoreJockerSerie, nbTuillesSerieJocker;

        /*-- Traitement --*/
        tabScore         = new int[] { 0, 1, 3, 5, 7, 9, 11, 15, 20, 25, 30, 35, 40, 50, 60, 70, 85, 100, 150, 300 };
        score            = 0;
        nbTuilleCroiss   = 1;
        scoreJockerSerie = 0;
        nbTuillesSerieJocker  = 0;
      
        if (difficulte == 2)
        {
            tabScore[5]  = 3;
            tabScore[11] = 20;
            tabScore[16] = 50;
        }

        Console.println(grille[0]);

        for (i = 1; i < grille.length; i++)
        {
        	if (Streams.debug)
        	{
           		Console.println("Analyse du nombre : " + grille[i]);
            	Console.println("\tValeur de  \"scoreJockerSerie\" : " + scoreJockerSerie);
        	}

            if (grille[i] == Streams.JOCKER) 
            {
                // On commence une nouvelle série si le jocker n'est pas dans une série croissante et qu'il ne se trouve pas en fin de grille
                if (i < (grille.length - 1) && (grille[i+1] < grille[i-1]))
                {
                	if (Streams.debug)
                	{
                		Console.println("\tJOCKER");
                		Console.println("\tNouvelle série");
                		Console.println("\tScore série précédente : " + tabScore[nbTuilleCroiss-1]);
                		Console.println("\tNb tuiles série prec : " + nbTuilleCroiss);
                	}

                    nbTuillesSerieJocker = nbTuilleCroiss;

                    nbTuilleCroiss++; // On ajoute le jocker à la série actuelle

                    scoreJockerSerie = tabScore[nbTuilleCroiss-1];
                    score += scoreJockerSerie;

                    nbTuilleCroiss = 0;
                    i++; // On passe au nombre n+2 à la fin de l'itération pour ne pas vérifier le jocker
                }

                nbTuilleCroiss++;
            }
            else if (grille[i] < grille[i-1]) 
            {
                // Si le jocker était dans la série prec, on vérifie si la série actuelle rapporte un meilleur score
                if (scoreJockerSerie > 0)
                {
                	// La série actuelle offre un meilleur score que la série prec avec le jocker
                	if (nbTuillesSerieJocker < nbTuilleCroiss)
                	{
                    	nbTuilleCroiss++; // On ajoute le jocker à la série actuelle
                    	score = score - scoreJockerSerie +  tabScore[nbTuillesSerieJocker-1]; // On enlève le jocker de la série précédente
                	}

                	nbTuillesSerieJocker = 0;
                	scoreJockerSerie 	 = 0;
                }

                if (Streams.debug)
                {
                	Console.println("\tNouvelle série");
               		Console.println("\tScore série précédente : " + tabScore[nbTuilleCroiss-1]);
               		Console.println("\tNb tuiles série prec : " + nbTuilleCroiss);
                }

                score += tabScore[nbTuilleCroiss-1];

                nbTuilleCroiss = 1;
            }
            else
            {
                nbTuilleCroiss++;
            }
        }

        if (Streams.debug)
        {
            Console.println("\tScore série précédente : " + tabScore[nbTuilleCroiss-1]);
			Console.println("\tNb tuiles série prec : " + nbTuilleCroiss);
			Console.println("Fin de l'analyse des nombres");
        }

        if (scoreJockerSerie > 0 && nbTuillesSerieJocker < nbTuilleCroiss)
        {
            nbTuilleCroiss++; // On ajoute le jocker
            score = score - scoreJockerSerie +  tabScore[nbTuillesSerieJocker-1];

            scoreJockerSerie = 0;
        }

        score += tabScore[nbTuilleCroiss-1];

        return score;
    }

    private static String grilleEnChaine(int[] grille)
    {

        /*-- Déclaration des variables --*/
        String sRet;
        int i;
      
        /*-- Traitement --*/
        sRet = "";
        
        for (i = 0; i < grille.length; i++)
        {
            if (grille[i] == 0)
                sRet += "_ ";
            else if (grille[i] == Streams.JOCKER)
                sRet += "J ";
            else
                sRet += grille[i] + " ";
        }
        
        return sRet;
    }
}