import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SelectableModule from './selectable-module';
import SelectableModuleDetail from './selectable-module-detail';
import SelectableModuleUpdate from './selectable-module-update';
import SelectableModuleDeleteDialog from './selectable-module-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SelectableModuleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SelectableModuleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SelectableModuleDetail} />
      <ErrorBoundaryRoute path={match.url} component={SelectableModule} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SelectableModuleDeleteDialog} />
  </>
);

export default Routes;
