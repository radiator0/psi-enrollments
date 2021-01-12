import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClassUnit from './class-unit';
import ClassUnitDetail from './class-unit-detail';
import ClassUnitUpdate from './class-unit-update';
import ClassUnitDeleteDialog from './class-unit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClassUnitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClassUnitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClassUnitDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClassUnit} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClassUnitDeleteDialog} />
  </>
);

export default Routes;
