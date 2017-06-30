import { Team2Angular2UIPage } from './app.po';

describe('team2-angular2-ui App', () => {
  let page: Team2Angular2UIPage;

  beforeEach(() => {
    page = new Team2Angular2UIPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
