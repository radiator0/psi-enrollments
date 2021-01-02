import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FieldOfStudy from './field-of-study';
import FieldOfStudyDetail from './field-of-study-detail';
import FieldOfStudyUpdate from './field-of-study-update';
import FieldOfStudyDeleteDialog from './field-of-study-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FieldOfStudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FieldOfStudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FieldOfStudyDetail} />
      <ErrorBoundaryRoute path={match.url} component={FieldOfStudy} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FieldOfStudyDeleteDialog} />
  </>
);

export default Routes;
