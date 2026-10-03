import { Link } from "react-router-dom";

function ExperienceCard({ experience }) {
  const icons = {
    Movie: "🎬",
    Event: "🎵",
    Sports: "🏟️",
    Theatre: "🎭",
  };

  return (
    <Link
      to={`/experience/${experience.id}`}
      className="group overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm transition hover:-translate-y-1 hover:shadow-xl"
    >
      <div className="flex h-48 items-center justify-center bg-gradient-to-br from-indigo-600 via-purple-600 to-slate-950 text-6xl">
        {experience.imageUrl ? (
          <img
            src={experience.imageUrl}
            alt={experience.title}
            className="h-full w-full object-cover"
          />
        ) : (
          icons[experience.category] || "🎟️"
        )}
      </div>

      <div className="p-5">
        <div className="flex items-center justify-between gap-3">
          <span className="rounded-full bg-indigo-50 px-2.5 py-1 text-xs font-bold text-indigo-600">
            {experience.category}
          </span>

          <span className="text-sm font-bold text-slate-900">
            From ₹{experience.startingPrice ?? experience.price ?? 0}
          </span>
        </div>

        <h3 className="mt-3 text-lg font-bold text-slate-900 group-hover:text-indigo-600">
          {experience.title}
        </h3>

        <p className="mt-1 text-sm text-slate-500">
          {experience.genre} • {experience.language}
        </p>

        <p className="mt-3 text-sm text-slate-500">
          📍 {experience.city}
        </p>

        {experience.durationMinutes && (
          <p className="mt-1 text-sm text-slate-400">
            ⏱️ {experience.durationMinutes} minutes
          </p>
        )}
      </div>
    </Link>
  );
}

export default ExperienceCard;