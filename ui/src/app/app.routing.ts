import { Routes, RouterModule } from '@angular/router';
import { DealSetupComponent } from './deal/index';
import { TapecrackingComponent } from './tapeCracking/index';
import { TaskDetailsComponent } from './task-details/index';
import { TaskComponent } from './task/index';
import { AuthGuard } from './guards/index';

const appRoutes: Routes = [
          { path: '', component: DealSetupComponent },
          { path: 'taskDetails/:loanId/:taskName', component: TaskDetailsComponent },
           { path: 'deal', component: DealSetupComponent },
           { path: 'tapeCracking/:dealId', component: TapecrackingComponent },
           { path: 'taskDashboard', component: TaskComponent },
          
           
       ];
export const routing = RouterModule.forRoot(appRoutes);