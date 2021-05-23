using System;
using System.Linq;
using System.IO;
using System.Collections.Generic;
using System.Text;

namespace Generate_ParseTable
{
    class ParseTable
    {
        public List<List<int>> table = new List<List<int>>();

        public ParseTable()
        {
            GenerateTable();
        }

        void GenerateTable()
        {
            List<string> nonTerminals = Data.getNonTerminals();
            List<string> terminals = Data.getTerminals();
            Console.WriteLine(nonTerminals.Count);
            for (int i = 0; i < nonTerminals.Count; i++)
            {
                table.Add(new List<int>());
                foreach (string item in terminals)
                {
                    table[i].Add(0);
                }
            }

            foreach (string A in nonTerminals)
            {
                foreach (int p in ProductionsFor(A))
                {
                    foreach (string a in Predict(p))
                    {
                        string b = a.TrimStart(' ').ToLower();
                        Console.WriteLine(nonTerminals.IndexOf(A) + " " + terminals.IndexOf(b));
                        Console.WriteLine(b + " " + p);
                        if (table[nonTerminals.IndexOf(A)][terminals.IndexOf(b)] == 0)
                        {
                            table[nonTerminals.IndexOf(A)][terminals.IndexOf(b)] = p;
                        }
                        else
                        {
                            throw new Exception($"Not ll1 Problem found at {A}, {b}, {p}, {table[nonTerminals.IndexOf(A)][terminals.IndexOf(b)]}");
                        }
                    }
                }
            }

        }

        static List<int> ProductionsFor(string A)
        {
            List<int> Productions = new List<int>();

            using (StreamReader sr = new StreamReader(Data.FileLocation(@"BNF.txt")))
            {
                int i = 0;
                string line = null;
                while ((line = sr.ReadLine()) != null)
                {
                    i++;
                    string[] linearr = line.Split('⟶');
                    if (linearr[0] == A)
                    {
                        Productions.Add(i);
                    }
                }
            }
            return Productions;
        }

        static List<string> Predict(int p)
        {
            List<string> predicts = new List<string>(); 

            using (StreamReader sr = new StreamReader(Data.FileLocation(@"Predictsets.txt")))
            {
                string line = null;
                while ((line = sr.ReadLine()) != null)
                {
                    string[] lineArr = line.Split(',');
                    
                    if (Convert.ToInt32(lineArr[0]) == p)
                    {
                        for (int i = 1; i < lineArr.Length; i++)
                        {
                            lineArr[i].TrimStart(' ');
                            lineArr[i].TrimEnd(' ');

                            predicts.Add(lineArr[i]);
                        }
                        break;
                    }
                }
            }
            return predicts;
        }

    }
}
