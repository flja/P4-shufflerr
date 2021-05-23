using System;
using System.IO;
using System.Collections.Generic;

namespace BNF___Calculator
{
    class Program
    {
        static void Main(string[] args)
        {
            using (StreamWriter sw = new StreamWriter(FileLocation(@"Firstsets.txt")))
            {
                string Firstsets = "";
                foreach (string item in FirstSets.GetFirstSets())
                {
                    Firstsets += item;
                    Firstsets += "\n";
                }
                sw.Write(Firstsets);
            }
            using (StreamWriter sw = new StreamWriter(FileLocation(@"Followsets.txt")))
            {
                string Followsets = "";
                foreach (string item in FollowSets.GetFollowSets())
                {
                    Followsets += item;
                    Followsets += "\n";
                }
                sw.Write(Followsets);
            }
            using (StreamWriter sw = new StreamWriter(FileLocation(@"Predictsets.txt")))
            {
                string predictsets = "";
                foreach (string item in Predictsets.Getpredictsets())
                {
                    predictsets += item;
                    predictsets += "\n";
                }
                sw.Write(predictsets);
            }
        }

        static string FileLocation(string Filename)
        {
            string path = Directory.GetCurrentDirectory();
            path = Directory.CreateDirectory(Path.Combine(path, @"FinalSets")).ToString();
            path = Path.Combine(path, Filename);
            return path;
        }
    }
}
