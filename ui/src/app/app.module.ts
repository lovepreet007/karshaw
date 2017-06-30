import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { DealSetupComponent } from './deal/index';
import { routing }        from './app.routing';
import { TapecrackingComponent } from './tapeCracking/index';
import { TaskComponent } from './task/index';
import { AuthGuard } from './guards/index';
import { AlertService,  DealService } from './services/index';
import { AppComponent } from './app.component';
import {ReactiveFormsModule} from '@angular/forms';
import { TaskDetailsComponent } from './task-details/index';

@NgModule({
    imports: [
              BrowserModule,
              FormsModule,
              HttpModule,
              routing,
              ReactiveFormsModule
             ],
            
          declarations: [
              AppComponent,
              DealSetupComponent,
              TapecrackingComponent,
              TaskComponent,              
              TaskDetailsComponent
          ],
  providers: [
              AuthGuard,
              AlertService,
              DealService],
  bootstrap: [AppComponent]
})
export class AppModule { }
