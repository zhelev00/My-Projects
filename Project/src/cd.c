#include "common.h"

static void tick(void);
static void touch(Entity *other);

void initCD(char *line)
{
	Entity *e;

	e = malloc(sizeof(Entity));
	memset(e, 0, sizeof(Entity));
	stage.entityTail->next = e;
	stage.entityTail = e;

	sscanf(line, "%*s %f %f", &e->x, &e->y);

	e->health = 1;

	e->texture = loadTexture("gfx/cd.png");
	SDL_QueryTexture(e->texture, NULL, NULL, &e->w, &e->h);
	e->flags = EF_WEIGHTLESS;
	e->tick = tick;
	e->touch = touch;

}

static void tick(void)
{
	self->value += 0.1;

	self->y += sin(self->value);
}

static void touch(Entity *other)
{
	if (self->health > 0 && other == player)
	{
		self->health = 0;
        
		loadMusic("music/one_1.mp3");
        playMusic(1);
    }
}