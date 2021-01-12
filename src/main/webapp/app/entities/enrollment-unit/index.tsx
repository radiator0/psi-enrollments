import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EnrollmentUnit from './enrollment-unit';
import EnrollmentUnitDetail from './enrollment-unit-detail';
import EnrollmentUnitUpdate from './enrollment-unit-update';
import EnrollmentUnitDeleteDialog from './enrollment-unit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EnrollmentUnitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EnrollmentUnitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EnrollmentUnitDetail} />
      <ErrorBoundaryRoute path={match.url} component={EnrollmentUnit} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EnrollmentUnitDeleteDialog} />
  </>
);

export default Routes;
