package com.yanwenli.prd_2;

import java.util.ArrayList;

public class LoadInuitInfo {

    private ArrayList<Inuit> listInuitsInfo = new ArrayList<>();

    public ArrayList<Inuit> getListInuitsInfo() {
        return this.listInuitsInfo;
    }

    public void setListInuitsInfo(ArrayList<Inuit> listInuitsInfo) {
        this.listInuitsInfo = listInuitsInfo;
    }

    public LoadInuitInfo() {

    }

    /*
        public LoadInuitInfo() {
            listInuitsInfo.add(new Inuit(0, "Titre : L'homme prépare le poisson et la femme assouplit les kamiks\n" +
                    "Artiste :QUMALUK, Levi\n" +
                    "Date création :1986\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));

            listInuitsInfo.add(new Inuit(1, "Titre : Aussitôt arrivé au site, on installe le camp\n" +
                    "Artiste :QUMALUK, Mary\n" +
                    "Date création :1987\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));

            listInuitsInfo.add(new Inuit(2, "Titre : Un voyageur redresse les harnais avant le départ\n" +
                    "Artiste :QUMALUK, Caroline\n" +
                    "Date création :1986\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));

            listInuitsInfo.add(new Inuit(3, "Titre : Un homme aide deux femmes qui cousent des peaux sur un kayak\n" +
                    "Artiste :QUMALUK, Levi\n" +
                    "Date création :1982\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));

            listInuitsInfo.add(new Inuit(4, "Titre : Un homme et sa femme font un kayak\n" +
                    "Artiste :QUMALUK, Levi\n" +
                    "Date création :1983\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));

            listInuitsInfo.add(new Inuit(5, "Titre : Rassemblement d'oiseaux dans l'Arctique\n" +
                    "Artiste :PAPIALUK, Josie P\n" +
                    "Date création :1983\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));

            listInuitsInfo.add(new Inuit(6, "À la recherche d'un chasseur perdu sur les glaces\n" +
                    "Artiste :QUMALUK, Levi\n" +
                    "Date création :1984\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));

            listInuitsInfo.add(new Inuit(7, "Les faiseurs de kayak\n" +
                    "Artiste :QUMALUK, Levi\n" +
                    "Date création :\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));

            listInuitsInfo.add(new Inuit(8, "Les campeurs apprêtent une peau d'ours\n" +
                    "Artiste :QUMALUK, Leah\n" +
                    "Date création :1985\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));

            listInuitsInfo.add(new Inuit(9, "Le retour des parents\n" +
                    "Artiste :QUMALUK, Levi\n" +
                    "Date création :1983\n" +
                    "Médium :Estampe, gravure sur pierre, papier Kozo\n"));
        }
    */
    public ArrayList<Inuit> createListInuitInfo(){
        listInuitsInfo.add(new Inuit(0, "L'homme prépare le poisson et la femme assouplit les kamiks\n",
                "QUMALUK, Levi\n",
                "1986\n",
                "Estampe, gravure sur pierre, papier Kozo\n"));

        listInuitsInfo.add(new Inuit(1, "Titre : Aussitôt arrivé au site, on installe le camp\n",
                "QUMALUK, Mary\n",
                "1987\n",
                "Estampe, gravure sur pierre, papier Kozo\n"));

        listInuitsInfo.add(new Inuit(2, "Titre : Un voyageur redresse les harnais avant le départ\n",
                "QUMALUK, Caroline\n",
                "1986\n",
                "Estampe, gravure sur pierre, papier Kozo\n"));

        listInuitsInfo.add(new Inuit(3, "Titre : Un homme aide deux femmes qui cousent des peaux sur un kayak\n",
                "QUMALUK, Levi\n",
                "1982\n",
                "Estampe, gravure sur pierre, papier Kozo\n"));

        listInuitsInfo.add(new Inuit(4, "Titre : Un homme et sa femme font un kayak\n",
                "QUMALUK, Levi\n",
                "1983\n",
                "Estampe, gravure sur pierre, papier Kozo\n"));

        listInuitsInfo.add(new Inuit(5, "Rassemblement d'oiseaux dans l'Arctique\n",
                "PAPIALUK, Josie P\n" ,
                "1983\n" ,
                "Estampe, gravure sur pierre, papier Kozo\n"));

        listInuitsInfo.add(new Inuit(6, "À la recherche d'un chasseur perdu sur les glaces\n",
                "QUMALUK, Levi\n",
                "1984\n",
                "Estampe, gravure sur pierre, papier Kozo\n"));

        listInuitsInfo.add(new Inuit(7, "Les faiseurs de kayak\n",
                "QUMALUK, Levi\n",
                "\n",
                "Estampe, gravure sur pierre, papier Kozo\n"));

        listInuitsInfo.add(new Inuit(8, "Les campeurs apprêtent une peau d'ours\n",
                "QUMALUK, Leah\n",
                "1985\n",
                "Estampe, gravure sur pierre, papier Kozo\n"));

        listInuitsInfo.add(new Inuit(9, "Le retour des parents\n",
                "QUMALUK, Levi\n",
                "1983\n",
                "Estampe, gravure sur pierre, papier Kozo\n"));


        return this.listInuitsInfo;
    }

}
