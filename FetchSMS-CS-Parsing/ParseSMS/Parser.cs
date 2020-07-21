using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ParseSMS
{
    public class Parser
    {
        public List<MMS> Parse(string json)
        {
            var list = JsonConvert.DeserializeObject<List<MMS>>(json);
            return list;
        }
    }
}
