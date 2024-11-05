Rendu Mathieu MORGAT :
- Sqlindex : fichiers sql pour les modèles projet et task (user non fait), avec :
    - Modèle
    - Index
    - Vue matérialisée
- Entity : ajout BatchSize
- Repository : 
  - Fix query N+1 avec les requêtes JPQL (uniquement pour les findAll)
  - Début implémentation de Pageable pour findAll()
- Service : 
  - jdbcTemplate pour tasks_per_project
  - Tentative de caching du UserService
  - Tentative d'implémentation de Pageable

Le build n'as pas été testé
