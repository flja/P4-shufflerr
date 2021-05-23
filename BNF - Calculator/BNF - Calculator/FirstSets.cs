using System;
using System.IO;
using System.Collections.Generic;
using System.Text;

namespace BNF___Calculator
{
    static class FirstSets
    {
        public static List<string> GetFirstSets()
        {
            List<string> FirstSets = new List<string>();
            using (StreamReader sr = new StreamReader(FileLocation()))
            {
                string firstLine = sr.ReadLine();
                string[] terminals = firstLine.Split(';');
                string line = null;
                while ((line = sr.ReadLine()) != null)
                {
                    string[] currentTerminals = line.Split(';');
                    string LineFirstSet = $"First({currentTerminals[0]}) = {{";

                    for (int i = 1; i < currentTerminals.Length; i++)
                    {
                        if (currentTerminals[i] == "+")
                        {
                            LineFirstSet += terminals[i];
                            LineFirstSet += ", ";
                        }
                    }
                    LineFirstSet = LineFirstSet.TrimEnd(',', ' ');
                    LineFirstSet += "}";
                    FirstSets.Add(LineFirstSet);
                }
            }
            return FirstSets;
        }

        static string FileLocation()
        {
            string path = Directory.GetCurrentDirectory();
            path = Directory.CreateDirectory(Path.Combine(path, @"Tables")).ToString();
            path = Path.Combine(path, @"FirstSets.csv");
            return path;
        }
    }
}
