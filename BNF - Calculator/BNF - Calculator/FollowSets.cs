using System;
using System.IO;
using System.Collections.Generic;
using System.Text;

namespace BNF___Calculator
{
    static class FollowSets
    {
        public static List<string> GetFollowSets()
        {
            List<string> followSets = new List<string>();
            using (StreamReader sr = new StreamReader(FileLocation()))
            {
                string followLine = sr.ReadLine();
                string[] terminals = followLine.Split(';');
                string line = null;
                while ((line = sr.ReadLine()) != null)
                {
                    string[] currentTerminals = line.Split(';');
                    string LinefollowSet = $"follow({currentTerminals[0]}) = {{";

                    for (int i = 1; i < currentTerminals.Length; i++)
                    {
                        if (currentTerminals[i] == "+")
                        {
                            LinefollowSet += terminals[i];
                            LinefollowSet += ", ";
                        }
                    }
                    LinefollowSet = LinefollowSet.TrimEnd(',', ' ');
                    LinefollowSet += "}";
                    followSets.Add(LinefollowSet);
                }
            }
            return followSets;
        }

        static string FileLocation()
        {
            string path = Directory.GetCurrentDirectory();
            path = Directory.CreateDirectory(Path.Combine(path, @"Tables")).ToString();
            path = Path.Combine(path, @"followSets.csv");
            return path;
        }

    }
}
