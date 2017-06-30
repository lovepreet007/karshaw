export class Loan{
    
    constructor(
            public loanId: String,
            public borrowerName: String,    
            public ssn: String,     
            public loanType: String,    
            public loanAmount: Number,
            public status: String
            
            ){}
}