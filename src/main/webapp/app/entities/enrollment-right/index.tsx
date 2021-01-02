import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EnrollmentRight from './enrollment-right';
import EnrollmentRightDetail from './enrollment-right-detail';
import EnrollmentRightUpdate from './enrollment-right-update';
import EnrollmentRightDeleteDialog from './enrollment-right-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EnrollmentRightUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EnrollmentRightUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EnrollmentRightDetail} />
      <ErrorBoundaryRoute path={match.url} component={EnrollmentRight} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EnrollmentRightDeleteDialog} />
  </>
);

export default Routes;
