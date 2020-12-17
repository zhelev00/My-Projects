#include "common.h"

void loadNextLevel(void) 
{
    level.loadNextLevel++;
    stage.entityTail = &stage.entityHead;
    stage.pizzaFound = 0;
    stage.pizzaTotal = 0;
    initPlayer();
    initEntities();
    initMap();

}