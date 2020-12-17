#include "common.h"

static void tick(void);
static void touch(Entity *other);
void initEnemy(char *line)
{
	Entity *e;

	e = malloc(sizeof(Entity));
	memset(e, 0, sizeof(Entity));
	stage.entityTail->next = e;
	stage.entityTail = e;

	sscanf(line, "%*s %f %f %f %f", &e->sx, &e->sy, &e->ex, &e->ey);

	e->texture = loadTexture("gfx/enemy1.png");

	e->health = 1;
	e->touch = touch;
	e->texture = loadTexture("gfx/enemy1.png");
	SDL_QueryTexture(e->texture, NULL, NULL, &e->w, &e->h);
	e->tick = tick;

	
	e->x = e->sx;
	e->y = e->sy;
}

static void touch(Entity *other)
{
	if (self->health > 0 && other == player)
	{   
		player->health--;
		if (player->health == 0)
		{
			exit(1);
			player->x = player->y = 0;
		}
    }
}

static void tick(void)
{
	if (abs(self->x - self->sx) < ENEMY_SPEED && abs(self->y - self->sy) < ENEMY_SPEED)
	{
		calcSlope(self->ex, self->ey, self->x, self->y, &self->dx, &self->dy);

		self->dx *= ENEMY_SPEED;
		self->dy *= ENEMY_SPEED;
		self -> texture = loadTexture("gfx/enemy1.png");
	}

	if (abs(self->x - self->ex) < ENEMY_SPEED && abs(self->y - self->ey) < ENEMY_SPEED)
	{
		calcSlope(self->sx, self->sy, self->x, self->y, &self->dx, &self->dy);

		self->dx *= ENEMY_SPEED;
		self->dy *= ENEMY_SPEED;
		self -> texture = loadTexture("gfx/enemy2.png");
	}
}
