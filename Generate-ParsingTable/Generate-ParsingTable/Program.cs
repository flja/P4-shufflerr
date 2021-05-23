using System;
using System.IO;
using System.Collections.Generic;
using System.Text;

namespace Generate_ParseTable
{
    class Program
    {
        static void Main(string[] args)
        {
            ParseTable tableGenerator = new ParseTable();
            bool isll1 = true;
            List<string> nonterminals = Data.getNonTerminals();
            List<string> terminals = Data.getTerminals();
            List<List<int>> table = tableGenerator.table;
            using (StreamWriter sw = new StreamWriter(Data.FileLocation(@"parsetable.csv")))
            {
                string terminalLine = " ";
                foreach (string item in terminals)
                {

                    terminalLine += $";{item.ToLower()}";
                }
                sw.WriteLine(terminalLine);
                for (int i = 0; i < table.Count; i++)
                {
                    string line = nonterminals[i];
                    foreach (int item in table[i])
                    {
                        line += $";{item.ToString()}";
                    }
                    sw.WriteLine(line);
                }
            }
            for (int i = 0; i < terminals.Count; i++)
            {
                List<int> prods = new List<int>();
                foreach (List<int> item in table)
                {
                    if (item[i] != 0)
                    {
                        if (prods.Contains(item[i]))
                        {
                            Console.WriteLine(terminals[i] + " " + item[i]);
                            isll1 = false;
                            break;
                        }
                        prods.Add(item[i]);
                    }
                }
                if (!isll1)
                {
                    break;
                }
            }
            if (!isll1)
            {
                Console.WriteLine("Not ll(1)");
            }
            else 
            {
                Console.WriteLine("Language is LL1 (for now)");
            }
        }
    }
}
