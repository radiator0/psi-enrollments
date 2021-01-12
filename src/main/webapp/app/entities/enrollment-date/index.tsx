import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EnrollmentDate from './enrollment-date';
import EnrollmentDateDetail from './enrollment-date-detail';
import EnrollmentDateUpdate from './enrollment-date-update';
import EnrollmentDateDeleteDialog from './enrollment-date-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EnrollmentDateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EnrollmentDateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EnrollmentDateDetail} />
      <ErrorBoundaryRoute path={match.url} component={EnrollmentDate} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EnrollmentDateDeleteDialog} />
  </>
);

export default Routes;
