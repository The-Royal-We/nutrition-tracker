import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NutritionalData from './nutritional-data';
import NutritionalDataDetail from './nutritional-data-detail';
import NutritionalDataUpdate from './nutritional-data-update';
import NutritionalDataDeleteDialog from './nutritional-data-delete-dialog';

const NutritionalDataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NutritionalData />} />
    <Route path="new" element={<NutritionalDataUpdate />} />
    <Route path=":id">
      <Route index element={<NutritionalDataDetail />} />
      <Route path="edit" element={<NutritionalDataUpdate />} />
      <Route path="delete" element={<NutritionalDataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NutritionalDataRoutes;
