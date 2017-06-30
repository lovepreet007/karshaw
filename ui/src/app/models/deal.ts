import { Review } from './review';

export class Deal{
    
    constructor(
            public dealName: String,    
            public clientName: String,   
            public dealDate: Date,    
            public priority: String,    
            public reviewDeal: Review[],
            public status: String
            
            ){}
}