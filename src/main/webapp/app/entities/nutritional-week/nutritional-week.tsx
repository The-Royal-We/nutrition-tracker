import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INutritionalWeek } from 'app/shared/model/nutritional-week.model';
import { getEntities } from './nutritional-week.reducer';

export const NutritionalWeek = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const nutritionalWeekList = useAppSelector(state => state.nutritionalWeek.entities);
  const loading = useAppSelector(state => state.nutritionalWeek.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="nutritional-week-heading" data-cy="NutritionalWeekHeading">
        <Translate contentKey="nutritionTrackerApp.nutritionalWeek.home.title">Nutritional Weeks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="nutritionTrackerApp.nutritionalWeek.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/nutritional-week/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="nutritionTrackerApp.nutritionalWeek.home.createLabel">Create new Nutritional Week</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {nutritionalWeekList && nutritionalWeekList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalWeek.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalWeek.dateFrom">Date From</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalWeek.dateTo">Date To</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalWeek.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nutritionalWeekList.map((nutritionalWeek, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/nutritional-week/${nutritionalWeek.id}`} color="link" size="sm">
                      {nutritionalWeek.id}
                    </Button>
                  </td>
                  <td>
                    {nutritionalWeek.dateFrom ? (
                      <TextFormat type="date" value={nutritionalWeek.dateFrom} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {nutritionalWeek.dateTo ? (
                      <TextFormat type="date" value={nutritionalWeek.dateTo} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{nutritionalWeek.name}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nutritional-week/${nutritionalWeek.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/nutritional-week/${nutritionalWeek.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/nutritional-week/${nutritionalWeek.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="nutritionTrackerApp.nutritionalWeek.home.notFound">No Nutritional Weeks found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default NutritionalWeek;
