using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace FetchSMS_CS_Parsing
{
    class Program
    {
        static void Main(string[] args)
        {
            var json = File.ReadAllText("sms.json");
            var a = new ParseSMS.Parser().Parse(json);
            foreach (var sms in a)
            {
                Console.WriteLine(sms.Body);
            }
            Console.Read();
        }
    }
}
