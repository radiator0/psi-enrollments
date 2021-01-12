import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StudyProgram from './study-program';
import StudyProgramDetail from './study-program-detail';
import StudyProgramUpdate from './study-program-update';
import StudyProgramDeleteDialog from './study-program-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudyProgramUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudyProgramUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudyProgramDetail} />
      <ErrorBoundaryRoute path={match.url} component={StudyProgram} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StudyProgramDeleteDialog} />
  </>
);

export default Routes;
