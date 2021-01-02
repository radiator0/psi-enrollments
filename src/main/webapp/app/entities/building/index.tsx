import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Building from './building';
import BuildingDetail from './building-detail';
import BuildingUpdate from './building-update';
import BuildingDeleteDialog from './building-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BuildingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BuildingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BuildingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Building} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BuildingDeleteDialog} />
  </>
);

export default Routes;
