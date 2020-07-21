using System;

namespace ParseSMS
{
    public class MMS
    {
        public int Id { get; set; }
        public int ThreadId { get; set; }
        public String Address { get; set; }
        public long Date { get; set; }
        public long Date_Sent { get; set; }
        public int Protocol { get; set; }
        public int Read { get; set; }
        public int Status { get; set; }
        public int Type { get; set; }
        public int ReplyPathPresent { get; set; }
        public string Body { get; set; }
        public int Locked { get; set; }
        public int SubId { get; set; }
        public int ErrorCode { get; set; }
        public string Creator { get; set; }
        public int Seen { get; set; }
    }
}
