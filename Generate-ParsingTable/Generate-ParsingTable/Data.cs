using System;
using System.Linq;
using System.IO;
using System.Collections.Generic;
using System.Text;

namespace Generate_ParseTable
{
    static class Data
    {
       static public List<string> getTerminals()
       {
           SletTommeLinjerIFil(FileLocation(@"Lists.csv"));
           using (StreamReader sr = new StreamReader(FileLocation(@"Lists.csv")))
           {
                string terminals = sr.ReadLine().ToLower();
  
                return new List<string>(terminals.Split(';'));
           }
       }

       static public List<string> getNonTerminals()
       {
           using (StreamReader sr = new StreamReader(FileLocation(@"Lists.csv")))
           {

               sr.ReadLine();
               string nonTerminals = sr.ReadLine().TrimEnd(';', ' ');

               return new List<string>(nonTerminals.Split(';'));
           }
       }

       public static string FileLocation(string Filename)
       {
           string path = Directory.GetCurrentDirectory();
           path = Directory.CreateDirectory(Path.Combine(path, @"Tables")).ToString();
           path = Path.Combine(path, Filename);
           return path;
       }
        public static void SletTommeLinjerIFil(string fillokation)
        {
            if (File.Exists(fillokation))
            {
                IEnumerable<string> linjer = File.ReadAllLines(Path.Combine(fillokation)).Where(linje => !string.IsNullOrWhiteSpace(linje));
                File.WriteAllLines(Path.Combine(fillokation), linjer);
            }
        }
    }
}
