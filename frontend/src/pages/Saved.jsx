import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";

function Saved() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const load = async () => {
    try {
      setLoading(true);
      const response = await api.get("/saved");
      setItems(response.data || []);
    } catch (err) {
      setError(err.response?.data?.message || "Could not load saved experiences.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const unsave = async (experienceId) => {
    try {
      await api.delete(`/saved/${experienceId}`);
      setItems((current) =>
        current.filter((item) => item.experienceId !== experienceId)
      );
    } catch (err) {
      setError(err.response?.data?.message || "Could not remove this item.");
    }
  };

  return (
    <main className="mx-auto max-w-5xl px-6 py-12">
      <h1 className="text-4xl font-black text-slate-900">Saved experiences</h1>
      <p className="mt-2 text-slate-500">
        Keep a shortlist of events you want to revisit.
      </p>

      {loading && <p className="mt-8 text-slate-500">Loading saved items...</p>}

      {error && (
        <div className="mt-6 rounded-xl bg-red-50 p-4 text-red-600">{error}</div>
      )}

      {!loading && items.length === 0 && (
        <div className="mt-10 rounded-2xl border border-dashed border-slate-300 py-16 text-center">
          <h2 className="text-xl font-bold">Nothing saved yet</h2>
          <p className="mt-2 text-slate-500">
            Open an experience and tap Save to add it here.
          </p>
          <Link
            to="/explore"
            className="mt-5 inline-block rounded-xl bg-indigo-600 px-5 py-3 font-bold text-white"
          >
            Browse experiences
          </Link>
        </div>
      )}

      <div className="mt-8 grid gap-4">
        {items.map((item) => (
          <div
            key={item.id}
            className="flex flex-wrap items-center justify-between gap-4 rounded-2xl border border-slate-200 bg-white p-6"
          >
            <div>
              <p className="text-xs font-bold uppercase text-indigo-600">
                {item.category}
              </p>
              <h2 className="mt-1 text-xl font-black">{item.title}</h2>
              <p className="mt-1 text-sm text-slate-500">
                {item.city} · {item.genre} · ₹{item.startingPrice}
              </p>
            </div>
            <div className="flex gap-3">
              <Link
                to={`/experience/${item.experienceId}`}
                className="rounded-xl bg-indigo-600 px-4 py-2 text-sm font-bold text-white"
              >
                View
              </Link>
              <button
                onClick={() => unsave(item.experienceId)}
                className="rounded-xl border border-slate-200 px-4 py-2 text-sm font-semibold"
              >
                Remove
              </button>
            </div>
          </div>
        ))}
      </div>
    </main>
  );
}

export default Saved;
