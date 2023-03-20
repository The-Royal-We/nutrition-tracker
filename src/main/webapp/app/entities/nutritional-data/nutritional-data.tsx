import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INutritionalData } from 'app/shared/model/nutritional-data.model';
import { getEntities } from './nutritional-data.reducer';

export const NutritionalData = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const nutritionalDataList = useAppSelector(state => state.nutritionalData.entities);
  const loading = useAppSelector(state => state.nutritionalData.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="nutritional-data-heading" data-cy="NutritionalDataHeading">
        <Translate contentKey="nutritionTrackerApp.nutritionalData.home.title">Nutritional Data</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="nutritionTrackerApp.nutritionalData.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/nutritional-data/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="nutritionTrackerApp.nutritionalData.home.createLabel">Create new Nutritional Data</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {nutritionalDataList && nutritionalDataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.weight">Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.steps">Steps</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.sleep">Sleep</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.water">Water</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.protein">Protein</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.carbs">Carbs</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.fat">Fat</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.calories">Calories</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="nutritionTrackerApp.nutritionalData.nutritionalWeek">Nutritional Week</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nutritionalDataList.map((nutritionalData, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/nutritional-data/${nutritionalData.id}`} color="link" size="sm">
                      {nutritionalData.id}
                    </Button>
                  </td>
                  <td>{nutritionalData.weight}</td>
                  <td>{nutritionalData.steps}</td>
                  <td>{nutritionalData.sleep}</td>
                  <td>{nutritionalData.water}</td>
                  <td>{nutritionalData.protein}</td>
                  <td>{nutritionalData.carbs}</td>
                  <td>{nutritionalData.fat}</td>
                  <td>{nutritionalData.calories}</td>
                  <td>
                    {nutritionalData.date ? <TextFormat type="date" value={nutritionalData.date} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {nutritionalData.nutritionalWeek ? (
                      <Link to={`/nutritional-week/${nutritionalData.nutritionalWeek.id}`}>{nutritionalData.nutritionalWeek.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nutritional-data/${nutritionalData.id}`}
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
                        to={`/nutritional-data/${nutritionalData.id}/edit`}
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
                        to={`/nutritional-data/${nutritionalData.id}/delete`}
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
              <Translate contentKey="nutritionTrackerApp.nutritionalData.home.notFound">No Nutritional Data found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default NutritionalData;
