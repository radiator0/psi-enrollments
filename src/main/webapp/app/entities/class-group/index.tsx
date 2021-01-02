import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClassGroup from './class-group';
import ClassGroupDetail from './class-group-detail';
import ClassGroupUpdate from './class-group-update';
import ClassGroupDeleteDialog from './class-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClassGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClassGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClassGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClassGroup} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ClassGroupDeleteDialog} />
  </>
);

export default Routes;
