import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NutritionalData from './nutritional-data';
import NutritionalWeek from './nutritional-week';
import ApplicationUser from './application-user';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="nutritional-data/*" element={<NutritionalData />} />
        <Route path="nutritional-week/*" element={<NutritionalWeek />} />
        <Route path="application-user/*" element={<ApplicationUser />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
