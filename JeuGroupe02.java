import iut.algo.*;

public class JeuGroupe02
{
    private static boolean debug = true;

	public static void main(String[] args)
	{
      	final int TAILLE_GRILLE = 20;
      
		String[]  tabJeton;
		String[]  grille;
        String    tuile;

		int tour;
        int nbJeton;
      	int indice;

		
        tabJeton = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "11", "12", "12", "13", "13", "14", "14", "15", "15", "16", "16", "17", "17", "18", "18", "19", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "J" };
      	grille   = new String[TAILLE_GRILLE];

        nbJeton = tabJeton.length;

        for (indice = 0; indice < TAILLE_GRILLE; indice++)
        {
            grille [indice] = "_";
        }
    	
      	for (tour = 0; tour < 20; tour++)
      	{
            Console.effacerEcran();
            Console.println(afficherGrille(grille));

      		indice = (int)(Math.random() * (nbJeton+1));
			tuile = tabJeton[indice];

			while (indice < nbJeton)
			{
              	tabJeton[indice] = tabJeton[indice+1];
              	indice++;
            	nbJeton--;
          	}
			
          	grille = saisirPosition(grille, tuile);
      	}
    }
  
  	private static String[] positionnerTuile(String[] grille, String tuile)
    {
      	int position;
      
    	Console.print("Placez le nombre " + tuile + " dans votre tableau : ");
      	position = Console.lireInt();
      
      	while (position < 0 || position > 19 || grille[position] != "_")
        {
        	Console.print("Saisie invalide. Veuillez resaisir : ");
          	position = Clavier.lire_int();
        }

        grille[position] = tuile;
      	
        return grille;
    }
  
    private static compterPoints(String[] grille)
    {
        
    }

  	private static String afficherGrille(String[] grille)
    {
      	String sRet;
        int i;
      
      	sRet = "";
        
        for (i = 0; i < grille.length; i++)
        {
            sRet += grille[i] + " ";
        }
      	
		return sRet;
    }
}