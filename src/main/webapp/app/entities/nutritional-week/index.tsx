import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NutritionalWeek from './nutritional-week';
import NutritionalWeekDetail from './nutritional-week-detail';
import NutritionalWeekUpdate from './nutritional-week-update';
import NutritionalWeekDeleteDialog from './nutritional-week-delete-dialog';

const NutritionalWeekRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NutritionalWeek />} />
    <Route path="new" element={<NutritionalWeekUpdate />} />
    <Route path=":id">
      <Route index element={<NutritionalWeekDetail />} />
      <Route path="edit" element={<NutritionalWeekUpdate />} />
      <Route path="delete" element={<NutritionalWeekDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NutritionalWeekRoutes;
